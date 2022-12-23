package com.wagarcdev.der.presentation.screens.screen_create_or_edit_report

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddRoad
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.navigation.StepRoute
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.navigation.composeSlideAnimateScreen
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.navigation.navigate
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.navigation.navigateBackWithFallback
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.steps.FirstStep
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.steps.SecondStep
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsState
import com.wagarcdev.der.utils.Constants
import kotlinx.coroutines.flow.collectLatest

/**
 * Compose the Create Or Edit Report screen.
 *
 * @param modifier the [Modifier] to be applied on main container of this screen.
 * @param onNavigateBack callback to navigate back from this screen.
 * @param viewModel the [CreateOrEditReportViewModel]. Default is provided by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalAnimationApi::class)
@Composable
fun CreateOrEditReportScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    viewModel: CreateOrEditReportViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val stepsNavController = rememberAnimatedNavController()
    val context = LocalContext.current

    BackHandler(onBack = onNavigateBack)

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        AnimatedNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            navController = stepsNavController,
            startDestination = StepRoute.FirstStep.route
        ) {
            composeSlideAnimateScreen(route = StepRoute.FirstStep.route) {
                FirstStep(
                    modifier = Modifier.fillMaxSize(),
                    report = screenState.report,
                    changeName = { viewModel.changeName(value = it) },
                    changeRegionCode = { viewModel.changeRegionCode(value = it) },
                    changeHighway = { viewModel.changeHighway(value = it) },
                    changeCounty = { viewModel.changeCounty(value = it) },
                    changeContractor = { viewModel.changeContractor(value = it) },
                    changeAreaExtension = { viewModel.changeAreaExtension(value = it) },
                    changeSupervisor = { viewModel.changeSupervisor(value = it) },
                    changeClimate = { viewModel.changeClimate(value = it) },
                    changeDayPeriod = { viewModel.changeDayPeriod(value = it) },
                    onNavigateBack = onNavigateBack,
                    onNextStep = {
                        stepsNavController.navigate(destinationRoute = StepRoute.SecondStep.route)
                    }
                )
            }

            composeSlideAnimateScreen(route = StepRoute.SecondStep.route) {
                SecondStep(
                    modifier = Modifier.fillMaxSize(),
                    attachedImageNames = screenState.report.attachedImageNames,
                    onAddImage = { viewModel.addImage(uri = it) },
                    onRemoveImage = { viewModel.removeImage(index = it) },
                    onNavigateBack = onNavigateBack,
                    onPreviousStep = {
                        stepsNavController.navigateBackWithFallback(
                            currentRoute = StepRoute.SecondStep.route,
                            destinationRoute = StepRoute.FirstStep.route
                        )
                    },
                    onFinishSteps = {
                        when (viewModel.textButton.value) {
                            Constants.CREATE_PDF -> {
                                createAndUpdatePDF(viewModel)
                            }
                            Constants.SHARE_PDF -> {
                                sharePDF(viewModel, context)
                            }
                        }
                    },
                    textButton = viewModel.textButton.collectAsState().value,
                    onUpdateStep = { createAndUpdatePDF(viewModel) }
                )
            }
        }
    }
}


private fun createAndUpdatePDF(viewModel: CreateOrEditReportViewModel) {
    viewModel.createPdf().also {
        /*como navegar para a tela que lista os relatorios? seria interessante por isso aqui*/
    }
}

private fun sharePDF(viewModel: CreateOrEditReportViewModel, context: Context) {
    viewModel.createPdf().also {
        viewModel.fileState.value?.let {
            val contentUri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".fileprovider",
                it
            )
            val share = Intent()
            share.apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, contentUri)
                type = "application/pdf"
            }
            context.startActivity(share)
        }
    }
}