package com.wagarcdev.der.di

import com.wagarcdev.der.data.GoogleRepositoryImpl
import com.wagarcdev.der.data.SimpleUserRepositoryImpl
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindGoogleRepository(
        repository: GoogleRepositoryImpl
    ): GoogleRepository

    @Binds
    @Singleton
    fun bindSimpleUserRepository(
        repository: SimpleUserRepositoryImpl
    ): SimpleUserRepository
}