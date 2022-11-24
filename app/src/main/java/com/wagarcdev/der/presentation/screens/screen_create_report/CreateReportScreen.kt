package com.wagarcdev.der.presentation.screens.screen_create_report

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wagarcdev.der.presentation.screens.screen_create_report.navigation.StepRoute
import com.wagarcdev.der.presentation.screens.screen_create_report.navigation.composeSlideAnimateScreen
import com.wagarcdev.der.presentation.screens.screen_create_report.navigation.navigate
import com.wagarcdev.der.presentation.screens.screen_create_report.navigation.navigateBackWithFallback
import com.wagarcdev.der.presentation.screens.screen_create_report.steps.FirstStep
import com.wagarcdev.der.presentation.screens.screen_create_report.steps.SecondStep

/**
 * Composes the Create Report screen.
 *
 * @param modifier the [Modifier] to be applied on the main container of this screen.
 * @param onBack a lambda to handle when user press the back button from navigation bar and/or
 * other button to navigate back from screen (ie navigation icon from TopAppBar).
 * @param viewModel the [CreateReportViewModel] for this screen. Default instance of view model
 * is created by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalAnimationApi::class)
@Composable
fun CreateReportScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: CreateReportViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val stepsNavController = rememberAnimatedNavController()

    BackHandler(onBack = onBack)

    Scaffold(modifier = modifier) { innerPadding ->
        AnimatedNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            navController = stepsNavController,
            startDestination = StepRoute.FirstStep.route
        ) {
            composeSlideAnimateScreen(route = StepRoute.FirstStep.route) {
                FirstStep(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp),
                    report = screenState.report,
                    changeName = { viewModel.changeName(value = it) },
                    changeRegionCode = { viewModel.changeRegionCode(value = it) },
                    changeHighway = { viewModel.changeHighway(value = it) },
                    changeCounty = { viewModel.changeCounty(value = it) },
                    changeContractor = { viewModel.changeContractor(value = it) },
                    changeAreaExtension = { viewModel.changeAreaExtension(value = it) },
                    changeSupervisor = { viewModel.changeSupervisor(value = it) },
                    onBack = onBack,
                    onNext = {
                        stepsNavController.navigate(destinationRoute = StepRoute.SecondStep.route)
                    }
                )
            }

            composeSlideAnimateScreen(route = StepRoute.SecondStep.route) {
                SecondStep(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp),
                    attachedImageNames = screenState.report.attachedImageNames,
                    onAddImage = { viewModel.addImage(uri = it) },
                    onRemoveImage = { viewModel.removeImage(index = it) },
                    onBack = onBack,
                    onPrevious = {
                        stepsNavController.navigateBackWithFallback(
                            currentRoute = StepRoute.SecondStep.route,
                            destinationRoute = StepRoute.SecondStep.route
                        )
                    },
                    onFinish = {
                        // TODO
                    }
                )
            }
        }
    }
}