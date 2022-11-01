package com.wagarcdev.der.presentation.screens.screen_register

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wagarcdev.der.MainViewModel

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    mainViewModel: MainViewModel
) {

    val wannaRegisterState = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {  },
        content = { RegisterScreenContent(mainViewModel, wannaRegisterState) }
    )

}

