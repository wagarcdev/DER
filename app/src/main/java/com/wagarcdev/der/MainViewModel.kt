package com.wagarcdev.der

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.wagarcdev.der.data.RoomMethods
import com.wagarcdev.der.data.local.AppPreferences
import com.wagarcdev.der.domain.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, appPreferences: AppPreferences) :
    AndroidViewModel(application) {

    lateinit var navHostController: NavHostController
    private val _users: MutableStateFlow<Users?> = MutableStateFlow(null)
    val users: StateFlow<Users?> = _users
    private val _appPreference = appPreferences


    private val _simpleUser: MutableStateFlow<Users?> = MutableStateFlow(null)
    private val simpleUser: StateFlow<Users?> = _simpleUser

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }


    suspend fun signIn(
        id: String,
        email: String,
        displayName: String,
        photoUrl: String
    ): StateFlow<Users?> {
        delay(2000) // Simulating network call
        _users.value = Users(id, email, null, null, displayName, photoUrl, null, false)
        RoomMethods(getApplication()).createNewUserWithSignWithGoogle(users.value!!)
        return users
    }

    /**
     * method to save userId in datastore
     */
    suspend fun changeUserId(userId: String) {
        _appPreference.changeUserId(userId)
    }

    /**
     * Method to getUserId from datastore
     */
    suspend fun getUserIdFromDatastore(): String? {
        return _appPreference.getUserId()
    }


    suspend fun createNewSimpleUser(user: Users) {
        _simpleUser.value = user
        RoomMethods(getApplication()).createNewSimpleUser(simpleUser.value!!)
        Log.i("TAG", user.toString() + "etapa1")
    }

    suspend fun validateLogin(username: String): String {
        return RoomMethods(getApplication()).validateLogin(true, username)
    }

    /**
     * method to get user by id
     * @param id userId retrived by method getUserIdFromDatastore
     */
    suspend fun getUserById(id: String): Users {
        return RoomMethods(getApplication()).getUserById(id)
    }

    suspend fun getUserId(username: String): String {
        return RoomMethods(getApplication()).getUserId(true, username)
    }

}