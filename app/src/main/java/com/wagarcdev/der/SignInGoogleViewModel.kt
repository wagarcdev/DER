package com.wagarcdev.der

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.presentation.navigation.Screens
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInGoogleViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private var _userState = MutableLiveData<User?>()
    val user: LiveData<User?> = _userState


    suspend fun googleSignIn(
        task: Task<GoogleSignInAccount>?,
        context: Context,
        mainViewModel: MainViewModel,
        navHostController: NavHostController
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
                    photoUrl = account.photoUrl!!.toString(),
                    navHostController
                )
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Autenticação falhou ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

}