package com.wagarcdev.der.presentation.screens.screen_contracts

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.navigation.Screens

@Composable
fun ContractsScreenContent(
    mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1.0f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "" +
                    "WRITE ABOUT" +
                    ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                mainViewModel.navHostController.navigate(
                    route = Screens.MainScreen.name
                )
            }
        ) {
            Text(
                text = "Back to Main Screen"
            )
        }
    }
}