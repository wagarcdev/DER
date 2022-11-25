package com.wagarcdev.der

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.data.local.AppPreferences
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val googleRepository: GoogleRepository,
    private val simpleUserRepository: SimpleUserRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    // TODO(Provide an empty user instead of null)
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    // TODO(Provide an empty user instead of null)
    private val _simpleUser: MutableStateFlow<User?> = MutableStateFlow(null)
    private val simpleUser: StateFlow<User?> = _simpleUser

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }

    fun signIn(
        id: String,
        email: String,
        displayName: String,
        photoUrl: String
    ) {
        viewModelScope.launch {
            _user.value = User(id, email, null, null, displayName, photoUrl, null, false)
            googleRepository.createNewUserWithSignWithGoogle((user.value!!))
        }
    }

    /**
     * method to save userId in datastore
     */
    suspend fun changeUserId(userId: String) {
        appPreferences.changeUserId(userId)
    }

    /**
     * Method to getUserId from datastore
     */
    suspend fun getUserIdFromDatastore(): String? {
        return appPreferences.getUserId()
    }

    suspend fun createNewSimpleUser(user: User) {
        _simpleUser.value = user
        simpleUserRepository.createNewSimpleUser(simpleUser.value!!)
    }

    suspend fun validateLogin(username: String): String {
        return simpleUserRepository.validateLogin(true, username)
    }

    /**
     * method to get user by id
     * @param id userId retrived by method getUserIdFromDatastore
     */
    suspend fun getUserById(id: String): User {
        return simpleUserRepository.getUserById(id)
    }

    suspend fun getUserId(username: String): String {
        return simpleUserRepository.getUserId(true, username)
    }
}