package com.wagarcdev.der.presentation.screens.screen_login.google_one_tap_sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class OneTapSignInState {
    var opened by mutableStateOf(value = false)
        private set

    fun open() {
        opened = true
    }

    fun close() {
        opened = false
    }
}

@Composable
fun rememberOneTapSignInState(): OneTapSignInState =
    remember { OneTapSignInState() }