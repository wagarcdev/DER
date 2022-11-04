package com.wagarcdev.der.presentation.screens.screen_contracts

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.wagarcdev.der.MainViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContractsScreen(
    mainViewModel: MainViewModel
) {

    Scaffold(
        topBar = {  },
        content = { ContractsScreenContent(mainViewModel) }
    )

}

