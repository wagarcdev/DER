package com.wagarcdev.der

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.wagarcdev.der.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInGoogleViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
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
}