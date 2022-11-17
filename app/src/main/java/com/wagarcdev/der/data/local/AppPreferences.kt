package com.wagarcdev.der.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Class to access to DataStore.
 */
class AppPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val tag = this::class.java.simpleName

    /**
     * Flowable string from user id or null if nothing is saved.
     */
    val userIdStream: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.userId]
    }

    /**
     * Get the string from user id or null if nothing is saved.
     */
    suspend fun getUserId(): String? = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.userId]
    }.first()

    /**
     * Change the stored user id.
     *
     * @param userId string value that represents the user id.
     */
    suspend fun changeUserId(userId: String) {
        try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.userId] = userId
            }
        } catch (exception: Exception) {
            exception.localizedMessage?.let { Log.e(tag, it) }
        }
    }

    private object PreferencesKeys {
        val userId = stringPreferencesKey(name = "user_id")
    }
}