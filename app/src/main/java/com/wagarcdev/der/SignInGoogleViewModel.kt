package com.wagarcdev.der

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.wagarcdev.der.data.RoomMethods
import com.wagarcdev.der.domain.model.UserGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInGoogleViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private var _userState = MutableLiveData<UserGoogle?>()
    val googleUser: LiveData<UserGoogle?> = _userState


    fun checkIfIsLogged(context: Context): Boolean {
        val gsa = GoogleSignIn.getLastSignedInAccount(context)
        return if (gsa != null) {
            viewModelScope.launch {
                _userState.value = UserGoogle(
                    id = gsa.id!!,
                    displayName = gsa.displayName!!,
                    email = gsa.email!!,
                    photoUrl = gsa.photoUrl!!.toString()
                )
            }
            true
        } else {
            false
        }
    }



}