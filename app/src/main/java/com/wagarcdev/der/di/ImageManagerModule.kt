package com.wagarcdev.der.di

import com.wagarcdev.der.utils.ImageManager
import com.wagarcdev.der.utils.ImageManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for [ImageManager].
 */
@Module
@InstallIn(SingletonComponent::class)
interface ImageManagerModule {
    @Binds
    @Singleton
    fun bindsImageManager(
        imageManager: ImageManagerImpl
    ): ImageManager
}