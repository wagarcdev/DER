package com.wagarcdev.der.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Class to access to DataStore.
 */
class AppPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    /**
     * Get the current user id.
     *
     * @return int value from current user id or 0 if there is no id saved.
     */
    suspend fun getCurrentUserId(): Int = dataStore.data
        .catch { exception ->
            exception.printStackTrace()
            emit(value = emptyPreferences())
        }
        .map { preferences ->
            preferences[PreferencesKeys.currentUserId] ?: 0
        }.first()

    /**
     * Change the current stored user id.
     *
     * @param userId int value that represents the user id.
     */
    suspend fun setCurrentUserId(userId: Int) {
        tryIt {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.currentUserId] = userId
            }
        }
    }

    /**
     * try/catch wrapper for an suspend block.
     */
    private suspend fun tryIt(action: suspend () -> Unit) {
        try {
            action()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private object PreferencesKeys {
        val currentUserId = intPreferencesKey(name = "current_user_id")
    }
}