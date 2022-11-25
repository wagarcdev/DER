package com.wagarcdev.der.presentation.screens.screen_contracts

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun ContractsScreen(
    navHostController: NavHostController
) {

    Scaffold(
        topBar = { },
        content = { ContractsScreenContent(navHostController) },
        backgroundColor = Color.Gray
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(
) {

    ContractsScreen(rememberAnimatedNavController())
}

