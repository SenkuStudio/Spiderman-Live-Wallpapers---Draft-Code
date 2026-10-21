package com.walli.spidermanlivewallpapers.domain.usecase

import com.walli.spidermanlivewallpapers.domain.repository.WallpaperRepository
import javax.inject.Inject

class IncrementDownloadUseCase @Inject constructor(
    private val repository: WallpaperRepository,
) {
    suspend operator fun invoke(wallpaperId: String) = repository.incrementDownload(wallpaperId)
}
