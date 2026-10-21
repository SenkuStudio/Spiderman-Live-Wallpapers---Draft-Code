package com.walli.spidermanaesthetic.ui.screens.home

import com.walli.spidermanaesthetic.domain.model.Wallpaper
import com.walli.spidermanaesthetic.domain.model.WallpaperCategory
import com.walli.spidermanaesthetic.domain.model.WallpaperSort
import com.walli.spidermanaesthetic.ui.common.LoadState

data class HomeUiState(
    val wallpapers: List<Wallpaper> = emptyList(),
    val recentWallpapers: List<Wallpaper> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val unlockedIds: Set<String> = emptySet(),
    val categories: List<WallpaperCategory> = listOf(WallpaperCategory(id = null, name = "All")),
    val selectedCategoryId: Int? = null,
    val sort: WallpaperSort = WallpaperSort.LATEST,
    val loadState: LoadState = LoadState.Loading,
    val hasNext: Boolean = true,
    val isOnline: Boolean = true,
    val wallpaperToUnlock: Wallpaper? = null,
    val featuredWallpapers: List<Wallpaper> = emptyList(),
    val isPremiumPassActive: Boolean = false,
    val currentPreviewIndex: Int? = null,
)
