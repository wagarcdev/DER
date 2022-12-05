package com.wagarcdev.der

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.wagarcdev.der.data.local.AppPreferences
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository
import com.wagarcdev.der.presentation.navigation.Screens
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens
import com.wagarcdev.der.presentation.navigation.graphs.AuthScreens
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

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }

}