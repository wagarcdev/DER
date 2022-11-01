package com.wagarcdev.der.presentation.screens.screen_auth

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.presentation.screens.screen_register.RegisterScreenContent
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    mainViewModel: MainViewModel
) {

    val wannaRegisterState = remember { mutableStateOf(false) }






    Scaffold(
        topBar = {  },
        content = {
            if (!wannaRegisterState.value) {
                AuthScreenContent(mainViewModel, wannaRegisterState)
            } else {
                RegisterScreenContent(mainViewModel, wannaRegisterState)
            }
        }
    )

}
