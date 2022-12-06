package com.wagarcdev.der.di

import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCaseImpl
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    @ViewModelScoped
    fun bindsValidateSimpleFieldUseCase(
        useCase: ValidateSimpleFieldUseCaseImpl
    ): ValidateSimpleFieldUseCase

    @Binds
    @ViewModelScoped
    fun bindsValidateEmailFieldUseCase(
        useCase: ValidateEmailFieldUseCaseImpl
    ): ValidateEmailFieldUseCase
}