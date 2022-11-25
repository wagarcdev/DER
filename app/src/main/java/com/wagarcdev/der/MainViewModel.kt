package com.wagarcdev.der

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.wagarcdev.der.data.local.AppPreferences
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository
import com.wagarcdev.der.navigation.Screens
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
    // TODO(REMOVE NavHostController from view model)
    lateinit var navHostController: NavHostController

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

    fun createGoogleUserInDatabase(
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

    suspend fun signInUser(
        password: MutableState<String>,
        username: MutableState<String>,
        mainViewModel: MainViewModel,
        context: Context
    ) {
        val comingPassword = mainViewModel.validateLogin(username = username.value)
        if (comingPassword != null) {
            if (comingPassword == password.value) {
                val userId = mainViewModel.getUserId(username.value)
                mainViewModel.changeUserId(userId)
                mainViewModel.navHostController.navigate(Screens.MainScreen.name)
            } else {
                Toast.makeText(context, "Senha incorreta", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Username incorreto", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkIfPasswordAreEquals(
        password: MutableState<String>,
        passwordConfirm: MutableState<String>
    ): Boolean {
        if (password.value == passwordConfirm.value) {
            return true
        }
        return false
    }


    suspend fun createSimpleUser(
        username: MutableState<String>,
        fullName: MutableState<String>,
        email: MutableState<String>,
        password: MutableState<String>,
        passwordConfirm: MutableState<String>,
        context: Context
    ) {
        if (username.value.isNotEmpty() && fullName.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && passwordConfirm.value.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                val isEqualsPassword = checkIfPasswordAreEquals(password, passwordConfirm)
                if (isEqualsPassword) {
                    val simpleUser =
                        User(
                            System.currentTimeMillis().toString(),
                            email.value,
                            username.value,
                            fullName.value,
                            fullName.value,
                            "",
                            password.value,
                            true
                        )
                    this.createNewSimpleUser(simpleUser).also {
                        this.navHostController
                            .navigate(Screens.AuthScreen.name)
                    }
                } else {
                    Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Email não está de acordo", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Preencha todos os campos corretamente", Toast.LENGTH_LONG)
                .show()
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