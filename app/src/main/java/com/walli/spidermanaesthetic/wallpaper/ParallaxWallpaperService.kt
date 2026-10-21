package com.walli.spidermanaesthetic.wallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.walli.spidermanaesthetic.data.settings.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@AndroidEntryPoint
class ParallaxWallpaperService : WallpaperService() {

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreateEngine(): Engine {
        return ParallaxEngine()
    }

    inner class ParallaxEngine : Engine(), SensorEventListener {
        private val scope = CoroutineScope(Dispatchers.Main + Job())
        private var sensorManager: SensorManager? = null
        private var rotationSensor: Sensor? = null
        
        private var backgroundBitmap: Bitmap? = null
        private val paint = Paint(Paint.FILTER_BITMAP_FLAG)
        
        private var scrollX = 0f
        private var scrollY = 0f
        
        private val maxOffset = 50f // Maximum parallax offset in pixels
        private val smoothing = 0.1f // Smoothing factor for sensor data
        
        private var targetOffsetX = 0f
        private var targetOffsetY = 0f
        private var currentOffsetX = 0f
        private var currentOffsetY = 0f

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            rotationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            
            loadWallpaper()
        }

        private fun loadWallpaper() {
            scope.launch {
                try {
                    val imagePath = settingsManager.parallaxWallpaperImagePath.first()
                    if (!imagePath.isNullOrEmpty()) {
                        val options = BitmapFactory.Options().apply {
                            inPreferredConfig = Bitmap.Config.ARGB_8888
                        }
                        backgroundBitmap = BitmapFactory.decodeFile(imagePath, options)
                        draw()
                    }
                } catch (e: Exception) {
                    Log.e("ParallaxService", "Error loading wallpaper", e)
                }
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                sensorManager?.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME)
            } else {
                sensorManager?.unregisterListener(this)
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            sensorManager?.unregisterListener(this)
            backgroundBitmap?.recycle()
            backgroundBitmap = null
        }

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)
                
                // Pitch (roll forward/backward) and Roll (tilt left/right)
                val pitch = orientation[1]
                val roll = orientation[2]
                
                targetOffsetX = -roll * maxOffset
                targetOffsetY = -pitch * maxOffset
                
                updateParallax()
            }
        }

        private fun updateParallax() {
            currentOffsetX += (targetOffsetX - currentOffsetX) * smoothing
            currentOffsetY += (targetOffsetY - currentOffsetY) * smoothing
            draw()
        }

        private fun draw() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null && backgroundBitmap != null) {
                    val bmp = backgroundBitmap!!
                    
                    val screenWidth = canvas.width
                    val screenHeight = canvas.height
                    
                    // Center crop logic with extra padding for parallax
                    val scaleX = (screenWidth + maxOffset * 2) / bmp.width
                    val scaleY = (screenHeight + maxOffset * 2) / bmp.height
                    val scale = maxOf(scaleX, scaleY)
                    
                    val bmpW = (bmp.width * scale).toInt()
                    val bmpH = (bmp.height * scale).toInt()
                    
                    val left = (screenWidth - bmpW) / 2 + currentOffsetX.toInt()
                    val top = (screenHeight - bmpH) / 2 + currentOffsetY.toInt()
                    
                    val destRect = Rect(left, top, left + bmpW, top + bmpH)
                    
                    canvas.drawBitmap(bmp, null, destRect, paint)
                }
            } finally {
                if (canvas != null) holder.unlockCanvasAndPost(canvas)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }
}
