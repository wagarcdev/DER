package com.wagarcdev.der

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.wagarcdev.der.data.RoomMethods
import com.wagarcdev.der.domain.model.SimpleUser
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
    private val _userGoogle: MutableStateFlow<UserGoogle?> = MutableStateFlow(null)
    val userGoogle: StateFlow<UserGoogle?> = _userGoogle

    private val _simpleUser: MutableStateFlow<SimpleUser?> = MutableStateFlow(null)
    private val simpleUser: StateFlow<SimpleUser?> = _simpleUser

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
        _userGoogle.value = UserGoogle(id, email, displayName, photoUrl)
        RoomMethods(getApplication()).createNewUserWithSignWithGoogle(userGoogle.value!!)
        return userGoogle
    }



    suspend fun createNewSimpleUser(user: SimpleUser) {
        _simpleUser.value = user
        RoomMethods(getApplication()).createNewSimpleUser(simpleUser.value!!)
        Log.i("TAG", user.toString() + "etapa1")
    }

    suspend fun getAllSimpleUser():List<SimpleUser> {
        return RoomMethods(getApplication()).getAllUsers()
    }

}