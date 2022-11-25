package com.wagarcdev.der

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.google.GoogleApiContract
import com.wagarcdev.der.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInGoogleViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private var _userState = MutableLiveData<User?>()
    val user: LiveData<User?> = _userState

    fun checkIfIsLogged(context: Context): Boolean {
        val gsa = GoogleSignIn.getLastSignedInAccount(context)
        return if (gsa != null) {
            viewModelScope.launch {
                _userState.value = User(
                    id = gsa.id!!,
                    email = gsa.email!!,
                    null,
                    null,
                    displayName = gsa.displayName!!,
                    photoUrl = gsa.photoUrl!!.toString(),
                    null,
                    false
                )
            }
            true
        } else {
            false
        }
    }

    fun googleSignIn(
        task: Task<GoogleSignInAccount>?,
        context: Context,
        mainViewModel: MainViewModel
    ) {
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                Toast.makeText(context, "Autenticação falhou", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mainViewModel.createGoogleUserInDatabase(
                    id = account.id!!,
                    email = account.email!!,
                    displayName = account.displayName!!,
                    photoUrl = account.photoUrl!!.toString()
                )
                val isReadyToGetTheUser = this.checkIfIsLogged(context)
                if (isReadyToGetTheUser) {
                    mainViewModel.navHostController
                        .navigate(Screens.MainScreen.name)
                }

            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Autenticação falhou ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }


}