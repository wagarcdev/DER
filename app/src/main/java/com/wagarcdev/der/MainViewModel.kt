package com.wagarcdev.der

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.wagarcdev.der.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    lateinit var navHostController: NavHostController
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    private val user: StateFlow<User?> = _user

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
    ): StateFlow<User?> {
        delay(2000) // Simulating network call
        _user.value = User(id, email, displayName, photoUrl)
        return user
    }


}