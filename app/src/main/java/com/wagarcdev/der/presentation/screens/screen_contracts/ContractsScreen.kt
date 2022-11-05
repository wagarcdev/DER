package com.wagarcdev.der.presentation.screens.screen_contracts

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun ContractsScreen(
    mainViewModel: MainViewModel,
    signInGoogleViewModel: SignInGoogleViewModel
) {

    Scaffold(
        topBar = { },
        content = { ContractsScreenContent(signInGoogleViewModel, mainViewModel) },
        backgroundColor = Color.Gray
    )



}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(
) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

    ContractsScreen(mainViewModel,signInGoogleViewModel)
}

