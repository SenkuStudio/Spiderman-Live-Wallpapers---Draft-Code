package com.walli.spidermanaesthetic.domain.model

enum class WallpaperSort(val label: String, val apiValue: String) {
    LATEST(label = "Latest", apiValue = "latest"),
//    TRENDING(label = "Trending", apiValue = "trending"),
    RANDOM(label = "Random", apiValue = "random"),
    LIVE(label = "Live", apiValue = "live"),
}
