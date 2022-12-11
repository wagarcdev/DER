package com.wagarcdev.der.domain.usecase

import com.wagarcdev.der.data.repository.AppPreferencesRepository
import javax.inject.Inject

/**
 * Save the logged user id.
 */
interface SetCurrentUserIdUseCase {
    /**
     * @param userId [Int] value of user id to be saved.
     */
    suspend operator fun invoke(userId: Int)
}

/**
 * Implementation of [SetCurrentUserIdUseCase] that uses [AppPreferencesRepository].
 */
class SetCurrentUserIdUseCaseImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) : SetCurrentUserIdUseCase {
    override suspend fun invoke(userId: Int) =
        appPreferencesRepository.setCurrentUserId(userId = userId)
}