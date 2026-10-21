package com.walli.spidermanaesthetic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.walli.spidermanaesthetic.domain.model.Wallpaper

@Entity(tableName = "wallpapers")
data class WallpaperEntity(
    @PrimaryKey val id: String,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val category: String,
    val downloads: Int,
    val createdAt: String,
    val isPremium: Boolean,
    val sortOrder: Int = 0,
    val videoUrl: String? = null,
    val wallpaperType: String? = null,
) {
    fun toDomain() = Wallpaper(
        id = id,
        title = title,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        category = category,
        downloads = downloads,
        createdAt = createdAt,
        isPremium = isPremium,
        isFavorite = false,
        videoUrl = videoUrl,
        wallpaperType = wallpaperType,
    )

    companion object {
        fun fromDomain(wallpaper: Wallpaper, index: Int) = WallpaperEntity(
            id = wallpaper.id,
            title = wallpaper.title,
            imageUrl = wallpaper.imageUrl,
            thumbnailUrl = wallpaper.thumbnailUrl,
            category = wallpaper.category,
            downloads = wallpaper.downloads,
            createdAt = wallpaper.createdAt,
            isPremium = wallpaper.isPremium,
            sortOrder = index,
            videoUrl = wallpaper.videoUrl,
            wallpaperType = wallpaper.wallpaperType,
        )
    }
}
