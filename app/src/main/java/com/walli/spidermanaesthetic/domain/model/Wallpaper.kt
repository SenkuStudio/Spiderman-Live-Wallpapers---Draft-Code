package com.walli.spidermanaesthetic.domain.model

data class Wallpaper(
    val id: String,
    val title: String,
    val category: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val downloads: Int,
    val createdAt: String,
    val isPremium: Boolean = false,
    val isUnlocked: Boolean = false,
    val isFavorite: Boolean = false,
    val isFeatured: Boolean = false,
    val blurHash: String? = null,
    val videoUrl: String? = null,
    val wallpaperType: String? = null,
) {
    val isLive: Boolean
        get() = wallpaperType?.equals("live", ignoreCase = true) == true ||
                videoUrl?.isNotEmpty() == true ||
                category.trim().equals("Live", ignoreCase = true) ||
                imageUrl.lowercase().contains(".mp4")

    val contentUrl: String
        get() = if (isLive) videoUrl ?: imageUrl else imageUrl
}
