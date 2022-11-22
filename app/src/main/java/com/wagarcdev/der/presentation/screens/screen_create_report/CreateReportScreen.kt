package com.wagarcdev.der.presentation.screens.screen_create_report

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NavigateBefore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Composes the Create Report screen.
 *
 * @param modifier the [Modifier] to be applied on the main container of this screen.
 * @param onBack a lambda to handle when user press the back button from navigation bar and/or
 * other button to navigate back from screen (ie navigation icon from TopAppBar).
 * @param viewModel the [CreateReportViewModel] for this screen. Default instance of view model
 * is created by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CreateReportScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: CreateReportViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    BackHandler(onBack = onBack)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Report") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Rounded.NavigateBefore,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .verticalScroll(state = scrollState)
        ) {
            ReportForm(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = screenState.text,
                textError = screenState.textError,
                changeText = { viewModel.changeText(value = it) }
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            ReportImages(
                modifier = Modifier.fillMaxWidth(),
                imagesBitmap = screenState.imagesBitmap,
                onAddImage = { viewModel.addImage(uri = it) },
                onRemoveImage = { viewModel.removeImage(index = it) }
            )
        }
    }
}