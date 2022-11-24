package com.wagarcdev.der.presentation.screens.screen_auth

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    mainViewModel: MainViewModel,
    signInGoogleViewModel: SignInGoogleViewModel
) {

    val wannaRegisterState = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {  },
        content = {

            if (!wannaRegisterState.value) {
                LoginContent(mainViewModel, wannaRegisterState, signInGoogleViewModel)
            } else { RegisterContent(mainViewModel, wannaRegisterState)
            }
        }
    )

}
