package com.wagarcdev.der.di

import com.wagarcdev.der.data.repository.AppPreferencesRepository
import com.wagarcdev.der.data.repository.AppPreferencesRepositoryImpl
import com.wagarcdev.der.data.repository.ReportsRepository
import com.wagarcdev.der.data.repository.ReportsRepositoryImpl
import com.wagarcdev.der.data.repository.UsersRepository
import com.wagarcdev.der.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsAppPreferencesRepository(
        repository: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository

    @Binds
    @Singleton
    fun bindsUsersRepository(
        repository: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    @Singleton
    fun bindsReportsRepository(
        repository: ReportsRepositoryImpl
    ): ReportsRepository
}