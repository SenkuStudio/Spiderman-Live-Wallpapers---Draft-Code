package com.walli.spidermanlivewallpapers.domain.usecase

import com.walli.spidermanlivewallpapers.domain.repository.WallpaperRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: WallpaperRepository,
) {
    suspend operator fun invoke() = repository.getCategories()
}
