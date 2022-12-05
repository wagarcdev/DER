package com.wagarcdev.der

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wagarcdev.der.data.local.AppPreferences
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens
import com.wagarcdev.der.presentation.navigation.graphs.AuthScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInGoogleViewModel @Inject constructor(
    private val googleRepository: GoogleRepository,
    private val simpleUserRepository: SimpleUserRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private var _username: MutableStateFlow<String?> = MutableStateFlow(null)
    val username: StateFlow<String?> = _username

    private var _typePassword: MutableStateFlow<String?> = MutableStateFlow(null)
    private var _userId: MutableStateFlow<String?> = MutableStateFlow(null)

    // TODO(Provide an empty user instead of null)
    private var _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user


    fun googleSignIn(
        task: Task<GoogleSignInAccount>?,
        context: Context,
        navHostController: NavHostController
    ) {
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                Toast.makeText(context, "Autenticação falhou", Toast.LENGTH_SHORT)
                    .show()
            } else {
                createGoogleUserInDatabase(
                    userId = account.id!!,
                    email = account.email!!,
                    displayName = account.displayName!!,
                    photoUrl = account.photoUrl!!.toString(),
                    navHostController
                )
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Autenticação falhou ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun createGoogleUserInDatabase(
        userId: String,
        email: String,
        displayName: String,
        photoUrl: String,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            _user.value = User(userId, email, displayName, null, null, photoUrl, null, false)
            googleRepository.createNewUserWithSignWithGoogle((user.value!!)).also {
                appPreferences.changeUserId(userId).also {
                    navHostController.navigate(AppScreens.Contracts.route)
                }
            }
        }
    }

    fun signInUser(
        password: MutableState<String>,
        username: MutableState<String>,
        context: Context,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            /**
             * line that return the password of user according the username
             */
            _typePassword.value = simpleUserRepository.validateLogin(true, username.value)
            if (_typePassword.value == password.value) {
                /**
                 * line that return the userId from user based in the username
                 */
                _userId.value = simpleUserRepository.getUserId(true, username.value)
                appPreferences.changeUserId(_userId.value!!).also {
                    navHostController.navigate(AppScreens.Contracts.route)
                }
            } else {
                Toast.makeText(context, "Senha incorreta", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * method to getUserId from datastore and pass the user from
     * other screen in case of there's no null
     * @param navHostController which is used to pass the user from other screen
     */
    fun checkIfUserAreLogged(navHostController: NavHostController) {
        viewModelScope.launch {
            if (!appPreferences.getUserId().isNullOrEmpty()) {
                navHostController.navigate(AppScreens.Contracts.route)
            }
        }
    }

    /**
     * method that recive the user and create a new user
     */
    fun createNewSimpleUser(comingUser: User) {
        viewModelScope.launch {
            _user.value = comingUser
            simpleUserRepository.createNewSimpleUser(user.value!!)
        }
    }

    /**
     * method to get user by id
     * @param id userId retrived by method getUserIdFromDatastore
     */
    fun getUserById() {
        viewModelScope.launch {
            _user.value = simpleUserRepository.getUserById(getUserIdFromDatastore().toString())
        }

    }

    /**
     * method that makes the validation of field of creation of user
     * check if the password that user has types are equals
     * create a object user and call the method
     */
    fun createSimpleUser(
        username: MutableState<String>,
        fullName: MutableState<String>,
        email: MutableState<String>,
        password: MutableState<String>,
        passwordConfirm: MutableState<String>,
        context: Context,
        navHostController: NavHostController
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
                        navHostController.navigate(AuthScreens.Login.route)
                        Log.i("TAG", "usuario criado: $simpleUser")
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

    fun validateState(
        fullName: MutableState<String>,
        email: MutableState<String>,
        password: MutableState<String>,
        passwordConfirm: MutableState<String>
    ): Boolean {
        return fullName.value.trim().isNotEmpty() && username.value!!.trim().isNotEmpty()
                && email.value.trim().isNotEmpty() && password.value.trim()
            .isNotEmpty() && passwordConfirm.value.trim()
            .isNotEmpty()
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

    suspend fun getUserIdFromDatastore(): String? {
        return appPreferences.getUserId()
    }


}