package com.wagarcdev.der.presentation.screens.screen_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.type.InputError
import com.wagarcdev.der.domain.type.InputResult
import com.wagarcdev.der.domain.usecase.GetUserByEmailUseCase
import com.wagarcdev.der.domain.usecase.InsertUserUseCase
import com.wagarcdev.der.domain.usecase.SetCurrentUserIdUseCase
import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed interface that represents one time events from view model to screen.
 */
sealed interface LoginChannel {
    object EmailNotFound : LoginChannel
    object IncorrectPassword : LoginChannel
    object SignInWithGoogleFailed : LoginChannel
    object SignInSuccessfully : LoginChannel
}

/**
 * State holder of screen and view model.
 */
data class LoginState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateSimpleFieldUseCase: ValidateSimpleFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val setCurrentUserIdUseCase: SetCurrentUserIdUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow(value = LoginState())
    val loginState = _loginState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _loginState.value
    )

    private val _channel = Channel<LoginChannel>()
    val channel = _channel.receiveAsFlow()

    fun changeEmail(value: String) {
        _loginState.update { it.copy(email = value) }
    }

    fun changePassword(value: String) {
        _loginState.update { it.copy(password = value) }
    }

    fun togglePasswordVisibility() {
        _loginState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun signInWithEmailAndPassword() {
        val emailResult = validateEmailFieldUseCase(string = _loginState.value.email)
        val passwordResult = validateSimpleFieldUseCase(string = _loginState.value.password)

        _loginState.update { state ->
            state.copy(
                emailError = when (emailResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (emailResult.inputError) {
                        InputError.FieldEmpty -> "Email cannot be empty"
                        InputError.FieldInvalid -> "This is not a valid email"
                        else -> null
                    }
                },
                passwordError = when (passwordResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (passwordResult.inputError) {
                        InputError.FieldEmpty -> "Password cannot be empty"
                        else -> null
                    }
                }
            )
        }

        listOf(
            emailResult,
            passwordResult
        ).any { inputResult ->
            inputResult is InputResult.Error
        }.also { hasError ->
            if (hasError) return
        }

        viewModelScope.launch {
            val userResult = getUserByEmailUseCase(email = _loginState.value.email)
            if (userResult == null) {
                _channel.send(element = LoginChannel.EmailNotFound)
                return@launch
            }

            val isValidPassword = userResult.password == _loginState.value.password
            if (!isValidPassword) {
                _channel.send(element = LoginChannel.IncorrectPassword)
                return@launch
            }

            setCurrentUserIdUseCase(userId = userResult.id)
            _channel.send(element = LoginChannel.SignInSuccessfully)
        }
    }

    fun signInWithGoogleAccount(user: User) {
        viewModelScope.launch {
            // todo maybe find a better way to improve this
            val userResult = getUserByEmailUseCase(email = user.email)
            if (userResult != null) {
                setCurrentUserIdUseCase(userId = userResult.id)
                _channel.send(element = LoginChannel.SignInSuccessfully)
            } else {
                val createdUserId = insertUserUseCase(user = user)
                if (createdUserId > 0) {
                    setCurrentUserIdUseCase(userId = createdUserId)
                    _channel.send(element = LoginChannel.SignInSuccessfully)
                } else _channel.send(element = LoginChannel.SignInWithGoogleFailed)
            }
        }
    }
}