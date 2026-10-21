package com.walli.spidermanlivewallpapers.di

import com.walli.spidermanlivewallpapers.data.repository.LibraryRepositoryImpl
import com.walli.spidermanlivewallpapers.data.repository.MockWallpaperRepositoryImpl
import com.walli.spidermanlivewallpapers.data.repository.WallpaperRepositoryImpl
import com.walli.spidermanlivewallpapers.domain.repository.LibraryRepository
import com.walli.spidermanlivewallpapers.domain.repository.WallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWallpaperRepository(
        impl: WallpaperRepositoryImpl,
    ): WallpaperRepository

    @Binds
    @Singleton
    abstract fun bindLibraryRepository(
        impl: LibraryRepositoryImpl,
    ): LibraryRepository
}
