package com.wagarcdev.der

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.wagarcdev.der.data.RoomMethods
import com.wagarcdev.der.domain.model.UserGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    lateinit var navHostController: NavHostController
    private val _user: MutableStateFlow<UserGoogle?> = MutableStateFlow(null)
    private val user: StateFlow<UserGoogle?> = _user

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
    ): StateFlow<UserGoogle?> {
        delay(2000) // Simulating network call
        _user.value = UserGoogle(id, email, displayName, photoUrl)
        return user
    }

    suspend fun createGoogleUserInLocalDatabase(userGoogle: UserGoogle) {
        RoomMethods(getApplication()).createNewUserWithSignWithGoogle(userGoogle)
    }

    suspend fun getAllGoogleUsers(): List<UserGoogle> {
        return RoomMethods(getApplication()).getAllGoogleUsers()
    }


}