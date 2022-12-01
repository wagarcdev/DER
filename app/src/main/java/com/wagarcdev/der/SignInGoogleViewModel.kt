package com.wagarcdev.der

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInGoogleViewModel @Inject constructor(
    private val googleRepository: GoogleRepository,
    private val simpleUserRepository: SimpleUserRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {
    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private var _userId: MutableStateFlow<String?> = MutableStateFlow(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    // TODO(Provide an empty user instead of null)
    private val _simpleUser: MutableStateFlow<User?> = MutableStateFlow(null)
    private val simpleUser: StateFlow<User?> = _simpleUser

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
                    id = account.id!!,
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
        id: String,
        email: String,
        displayName: String,
        photoUrl: String,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            _user.value = User(id, email, displayName, null, null, photoUrl, null, false)
            googleRepository.createNewUserWithSignWithGoogle((user.value!!)).also {
                changeUserId(id)
                navHostController
                    .navigate(AppScreens.Contracts.route)
            }
        }
    }

    fun signInUser(
        password: MutableState<String>,
        username: MutableState<String>,
        mainViewModel: MainViewModel,
        context: Context,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            val comingPassword = validateLogin(username = username.value)
            if (comingPassword == password.value) {
                val userId = getUserId(username.value)
                changeUserId(userId)
                navHostController.navigate(AppScreens.Contracts.route)
            } else {
                Toast.makeText(context, "Senha incorreta", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * method to save userId in datastore
     */
    fun changeUserId(userId: String) {
        viewModelScope.launch {
            appPreferences.changeUserId(userId)
        }

    }


    fun checkIfUserAreLogged(navHostController: NavHostController) {
        /**
         * recupera dados do usuario e checa se ele é nulo
         */
        viewModelScope.launch {
            if (!_userId.value.isNullOrEmpty()) {
                navHostController
                    .navigate(AppScreens.Contracts.route)
            }
        }

    }

    /**
     * Method to getUserId from datastore
     */
    fun getUserIdFromDatastore() {
        viewModelScope.launch {
            if (!appPreferences.getUserId().isNullOrEmpty()) {
                   _userId.value = appPreferences.getUserId()
            }
        }

    }

    fun createNewSimpleUser(user: User) {
        _simpleUser.value = user
        simpleUserRepository.createNewSimpleUser(simpleUser.value!!)
    }

    fun validateLogin(username: String): String {
        return simpleUserRepository.validateLogin(true, username)
    }

    /**
     * method to get user by id
     * @param id userId retrived by method getUserIdFromDatastore
     */
    fun getUserById(id: String): User {
        return simpleUserRepository.getUserById(id)
    }

    fun getUserId(username: String): String {
        return simpleUserRepository.getUserId(true, username)
    }

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


    private fun checkIfPasswordAreEquals(
        password: MutableState<String>,
        passwordConfirm: MutableState<String>
    ): Boolean {
        if (password.value == passwordConfirm.value) {
            return true
        }
        return false
    }


}