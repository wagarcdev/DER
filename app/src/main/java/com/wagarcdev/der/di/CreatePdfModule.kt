package com.wagarcdev.der.di

import com.wagarcdev.der.utils.CreatePdf
import com.wagarcdev.der.utils.CreatePdfImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for [CreatePdf].
 */
@Module
@InstallIn(SingletonComponent::class)
interface CreatePdfModule {
    @Binds
    @Singleton
    fun bindCreatePdf(
        generatePdf: CreatePdfImpl
    ): CreatePdf
}