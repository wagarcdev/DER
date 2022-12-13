package com.wagarcdev.der.di

import com.wagarcdev.der.domain.usecase.GetCurrentUserIdUseCase
import com.wagarcdev.der.domain.usecase.GetCurrentUserIdUseCaseImpl
import com.wagarcdev.der.domain.usecase.GetReportByIdStreamUseCase
import com.wagarcdev.der.domain.usecase.GetReportByIdStreamUseCaseImpl
import com.wagarcdev.der.domain.usecase.GetReportsForContractStreamUseCase
import com.wagarcdev.der.domain.usecase.GetReportsForContractStreamUseCaseImpl
import com.wagarcdev.der.domain.usecase.GetUserByEmailUseCase
import com.wagarcdev.der.domain.usecase.GetUserByEmailUseCaseImpl
import com.wagarcdev.der.domain.usecase.GetUserByIdStreamUseCase
import com.wagarcdev.der.domain.usecase.GetUserByIdStreamUseCaseImpl
import com.wagarcdev.der.domain.usecase.GetUserByIdUseCase
import com.wagarcdev.der.domain.usecase.GetUserByIdUseCaseImpl
import com.wagarcdev.der.domain.usecase.InsertReportUseCase
import com.wagarcdev.der.domain.usecase.InsertReportUseCaseImpl
import com.wagarcdev.der.domain.usecase.InsertUserUseCase
import com.wagarcdev.der.domain.usecase.InsertUserUseCaseImpl
import com.wagarcdev.der.domain.usecase.IsEmailAvailableUseCase
import com.wagarcdev.der.domain.usecase.IsEmailAvailableUseCaseImpl
import com.wagarcdev.der.domain.usecase.LogoutUseCase
import com.wagarcdev.der.domain.usecase.LogoutUseCaseImpl
import com.wagarcdev.der.domain.usecase.SetCurrentUserIdUseCase
import com.wagarcdev.der.domain.usecase.SetCurrentUserIdUseCaseImpl
import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCaseImpl
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Hilt module for use cases.
 */
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    @ViewModelScoped
    fun bindsSetCurrentUserIdUseCase(
        useCase: SetCurrentUserIdUseCaseImpl
    ): SetCurrentUserIdUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetCurrentUserIdUseCase(
        useCase: GetCurrentUserIdUseCaseImpl
    ): GetCurrentUserIdUseCase

    @Binds
    @ViewModelScoped
    fun bindsLogoutUseCase(
        useCase: LogoutUseCaseImpl
    ): LogoutUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetUserByIdStreamUseCase(
        useCase: GetUserByIdStreamUseCaseImpl
    ): GetUserByIdStreamUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetUserByIdUseCase(
        useCase: GetUserByIdUseCaseImpl
    ): GetUserByIdUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetUserByEmailUseCase(
        useCase: GetUserByEmailUseCaseImpl
    ): GetUserByEmailUseCase

    @Binds
    @ViewModelScoped
    fun bindsIsEmailAvailableUseCase(
        useCase: IsEmailAvailableUseCaseImpl
    ): IsEmailAvailableUseCase

    @Binds
    @ViewModelScoped
    fun bindsInsertUserUseCase(
        useCase: InsertUserUseCaseImpl
    ): InsertUserUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetReportsForContractStreamUseCase(
        useCase: GetReportsForContractStreamUseCaseImpl
    ): GetReportsForContractStreamUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetReportByIdStreamUseCase(
        useCase: GetReportByIdStreamUseCaseImpl
    ): GetReportByIdStreamUseCase

    @Binds
    @ViewModelScoped
    fun bindsInsertReportUseCase(
        useCase: InsertReportUseCaseImpl
    ): InsertReportUseCase

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