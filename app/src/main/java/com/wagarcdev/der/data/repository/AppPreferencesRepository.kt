package com.wagarcdev.der.data.repository

import com.wagarcdev.der.data.datasource.local.AppPreferences
import javax.inject.Inject

/**
 * Repository interface for application preferences/configurations.
 */
interface AppPreferencesRepository {
    /**
     * Save the logged user id.
     *
     * @param userId [Int] value of user id to be saved.
     */
    suspend fun setCurrentUserId(userId: Int)

    /**
     * Load the logged user id.
     *
     * @return [Int] value of the user id that is logged. 0 represents that there is no
     * currently user logged.
     */
    suspend fun getCurrentUserId(): Int
}

/**
 * Implementation of [AppPreferencesRepository] that uses [AppPreferences].
 */
class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : AppPreferencesRepository {
    override suspend fun setCurrentUserId(userId: Int) =
        appPreferences.setCurrentUserId(userId = userId)

    override suspend fun getCurrentUserId(): Int =
        appPreferences.getCurrentUserId()
}