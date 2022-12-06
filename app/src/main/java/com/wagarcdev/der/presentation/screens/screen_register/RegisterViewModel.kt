package com.wagarcdev.der.presentation.screens.screen_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * State holder of screen and view model.
 */
data class RegisterState(
    val name: String = "",
    val nameErrorResId: Int? = null,
    val email: String = "",
    val emailErrorResId: Int? = null,
    val username: String = "",
    val usernameErrorResId: Int? = null,
    val password: String = "",
    val passwordErrorResId: Int? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordErrorResId: Int? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _registerState = MutableStateFlow(value = RegisterState())

    val registerState = _registerState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _registerState.value
    )

    fun changeName(value: String) {
        _registerState.update { it.copy(name = value) }
    }

    fun changeEmail(value: String) {
        _registerState.update { it.copy(email = value) }
    }

    fun changeUsername(value: String) {
        _registerState.update { it.copy(username = value) }
    }

    fun changePassword(value: String) {
        _registerState.update { it.copy(password = value) }
    }

    fun changeRepeatedPassword(value: String) {
        _registerState.update { it.copy(repeatedPassword = value) }
    }
}