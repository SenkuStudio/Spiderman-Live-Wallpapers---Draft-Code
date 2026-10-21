package com.walli.spidermanlivewallpapers.domain.usecase

import com.walli.spidermanlivewallpapers.domain.repository.LibraryRepository
import javax.inject.Inject

class ObserveFavoriteIdsUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    operator fun invoke() = repository.observeFavoriteIds()
}
