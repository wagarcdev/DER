package com.wagarcdev.der.domain.usecase

import com.wagarcdev.der.data.repository.AppPreferencesRepository
import javax.inject.Inject

/**
 * Use case to logout the current user.
 */
interface LogoutUseCase {
    /**
     * @return true if the current user id was cleared, false otherwise.
     */
    suspend operator fun invoke(): Boolean
}

/**
 * Implementation of [LogoutUseCase] that uses [AppPreferencesRepository].
 */
class LogoutUseCaseImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) : LogoutUseCase {
    override suspend fun invoke(): Boolean {
        appPreferencesRepository.setCurrentUserId(userId = 0)
        return appPreferencesRepository.getCurrentUserId() == 0
    }
}