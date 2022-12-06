package com.wagarcdev.der.presentation.screens.screen_register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Compose the Register Screen.
 *
 * @param modifier the [Modifier] to be applied on main container of this screen.
 * @param onNavigateBack callback to navigate back from this screen.
 * @param viewModel the [RegisterViewModel]. Default is provided by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RegisterScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val screenState by viewModel.registerState.collectAsStateWithLifecycle()

    BackHandler(onBack = onNavigateBack)

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {

        }
    }
}