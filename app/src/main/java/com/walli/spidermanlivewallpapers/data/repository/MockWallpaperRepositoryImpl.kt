package com.walli.spidermanlivewallpapers.data.repository

import com.walli.spidermanlivewallpapers.domain.model.PagedResult
import com.walli.spidermanlivewallpapers.domain.model.Wallpaper
import com.walli.spidermanlivewallpapers.domain.model.WallpaperCategory
import com.walli.spidermanlivewallpapers.domain.model.WallpaperSort
import com.walli.spidermanlivewallpapers.domain.repository.WallpaperRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockWallpaperRepositoryImpl @Inject constructor() : WallpaperRepository {

    private val mockWallpapers = listOf(
        Wallpaper(
            id = "mock_1",
            title = "Spider-Man Aesthetic Red",
            category = "Aesthetic",
            imageUrl = "https://images.unsplash.com/photo-1635805737707-575885ab0820?auto=format&fit=crop&q=80&w=1000",
            thumbnailUrl = "https://images.unsplash.com/photo-1635805737707-575885ab0820?auto=format&fit=crop&q=80&w=400",
            downloads = 1540,
            createdAt = "2024-05-01",
            isPremium = false,
            isFeatured = true,
            blurHash = "L35Yxz%M00D%00_3RP?b00Rj~qxt"
        ),
        Wallpaper(
            id = "mock_2",
            title = "Spider-Man Neon Night",
            category = "Neon",
            imageUrl = "https://images.unsplash.com/photo-1612036782180-6f0b6cd846fe?auto=format&fit=crop&q=80&w=1000",
            thumbnailUrl = "https://images.unsplash.com/photo-1612036782180-6f0b6cd846fe?auto=format&fit=crop&q=80&w=400",
            downloads = 890,
            createdAt = "2024-05-02",
            isPremium = true,
            isFeatured = false,
            blurHash = "L14ySv0000_300~q00%M00Rj00ae"
        ),
        Wallpaper(
            id = "mock_live_1",
            title = "Live Wallpaper test 1",
            category = "Live",
            imageUrl = "https://cdn.senkustudio.com/wallpapers/originals/2026/10/live-wallpapers-test/live-frame-58af91ac95.webp",
            thumbnailUrl = "https://cdn.senkustudio.com/wallpapers/thumbs/2026/10/live-wallpapers-test/live-frame-58af91ac95.webp",
            downloads = 2500,
            createdAt = "2024-05-03",
            isPremium = true,
            isFeatured = true,
            blurHash = "L25Yxz%M00D%00_3RP?b00Rj~qxt",
            videoUrl = "https://cdn.senkustudio.com/wallpapers/live/2026/10/download-1-e9e36acef2.mp4",
            wallpaperType = "live"
        ),
        Wallpaper(
            id = "mock_3",
            title = "Spider-Verse Glitch",
            category = "Spider-Verse",
            imageUrl = "https://images.unsplash.com/photo-1608889175123-8ee362201f81?auto=format&fit=crop&q=80&w=1000",
            thumbnailUrl = "https://images.unsplash.com/photo-1608889175123-8ee362201f81?auto=format&fit=crop&q=80&w=400",
            downloads = 3200,
            createdAt = "2024-05-04",
            isPremium = false,
            isFeatured = false,
            blurHash = "L35Yxz%M00D%00_3RP?b00Rj~qxt"
        ),
        Wallpaper(
            id = "mock_4",
            title = "Miles Morales Urban",
            category = "Miles Morales",
            imageUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&q=80&w=1000",
            thumbnailUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&q=80&w=400",
            downloads = 1100,
            createdAt = "2024-05-05",
            isPremium = true,
            isFeatured = true,
            blurHash = "L35Yxz%M00D%00_3RP?b00Rj~qxt"
        ),
        Wallpaper(
            id = "mock_live_2",
            title = "Live Wallpaper test 2",
            category = "Live",
            imageUrl = "https://cdn.senkustudio.com/wallpapers/originals/2026/10/live-wallpapers-test/live-frame-d2f05e82e0.webp",
            thumbnailUrl = "https://cdn.senkustudio.com/wallpapers/thumbs/2026/10/live-wallpapers-test/live-frame-d2f05e82e0.webp",
            downloads = 4500,
            createdAt = "2024-05-06",
            isPremium = false,
            isFeatured = false,
            blurHash = "L35Yxz%M00D%00_3RP?b00Rj~qxt",
            videoUrl = "https://cdn.senkustudio.com/wallpapers/live/2026/10/download-2-153a64013c.mp4",
            wallpaperType = "live"
        )
    )

    override suspend fun getWallpapers(
        page: Int,
        limit: Int,
        categoryId: Int?,
        query: String?,
        sort: WallpaperSort
    ): Result<PagedResult<Wallpaper>> {
        delay(800)
        val filtered = if (!query.isNullOrBlank()) {
            mockWallpapers.filter { it.title.contains(query, ignoreCase = true) }
        } else if (categoryId != null) {
            mockWallpapers.filter { it.category.equals(getCategoryName(categoryId), ignoreCase = true) }
        } else {
            mockWallpapers
        }

        return Result.success(
            PagedResult(
                items = filtered,
                page = page,
                limit = limit,
                hasNext = false
            )
        )
    }

    private fun getCategoryName(id: Int): String = when(id) {
        1 -> "Aesthetic"
        2 -> "Neon"
        3 -> "Live"
        4 -> "Spider-Verse"
        5 -> "Miles Morales"
        else -> ""
    }

    override fun getLatestCachedWallpapers(limit: Int): Flow<List<Wallpaper>> {
        return flowOf(mockWallpapers.take(limit))
    }

    override suspend fun getCategories(): Result<List<WallpaperCategory>> {
        delay(400)
        return Result.success(
            listOf(
                WallpaperCategory(1, "Aesthetic", "aesthetic", "https://images.unsplash.com/photo-1635805737707-575885ab0820?w=200"),
                WallpaperCategory(2, "Neon", "neon", "https://images.unsplash.com/photo-1612036782180-6f0b6cd846fe?w=200"),
                WallpaperCategory(3, "Live", "live", "https://images.unsplash.com/photo-1534809027769-b00d750a6bac?w=200"),
                WallpaperCategory(4, "Spider-Verse", "spider-verse", "https://images.unsplash.com/photo-1608889175123-8ee362201f81?w=200"),
                WallpaperCategory(5, "Miles Morales", "miles-morales", "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200")
            )
        )
    }

    override suspend fun incrementDownload(wallpaperId: String): Result<Unit> {
        return Result.success(Unit)
    }
}
