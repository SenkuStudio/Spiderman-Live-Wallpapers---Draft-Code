package com.walli.spidermanlivewallpapers.domain.usecase

import com.walli.spidermanlivewallpapers.domain.model.Wallpaper
import com.walli.spidermanlivewallpapers.domain.repository.LibraryRepository
import javax.inject.Inject

class SaveRecentWallpaperUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    suspend operator fun invoke(wallpaper: Wallpaper) = repository.addRecent(wallpaper)
}
