package com.wagarcdev.der.di

import com.wagarcdev.der.utils.CoroutinesDispatchers
import com.wagarcdev.der.utils.CoroutinesDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for [CoroutinesDispatchers].
 */
@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {
    @Binds
    @Singleton
    fun bindsCoroutinesDispatchers(
        dispatchers: CoroutinesDispatchersImpl
    ): CoroutinesDispatchers
}