package com.walli.spidermanlivewallpapers.domain.usecase

import com.walli.spidermanlivewallpapers.domain.repository.LibraryRepository
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    operator fun invoke() = repository.observeFavorites()
}
