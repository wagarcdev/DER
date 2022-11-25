package com.wagarcdev.der.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel
import com.wagarcdev.der.presentation.screens.screen_auth.AuthScreen
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen
import com.wagarcdev.der.presentation.screens.screen_create_report.CreateReportScreen
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {

    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

    val navHostController = rememberAnimatedNavController()
    val context = LocalContext.current

    val isLogged = signInGoogleViewModel.checkIfIsLogged(context)

    NavHost(
        startDestination =
        if (isLogged) {
            Screens.MainScreen.name
        } else {
            Screens.AuthScreen.name
        },
        navController = navHostController
    ) {

        /** Main Screen */
        composable(route = Screens.MainScreen.name) {
            ContractsScreen(navHostController)
        }

        /** Authentication Screen */
        composable(route = Screens.AuthScreen.name) {
            AuthScreen(mainViewModel, signInGoogleViewModel)
        }

        /** Reports Screen */
        composable(route = Screens.ReportsScreen.name) {
            ReportsScreen(
                modifier = Modifier.fillMaxSize(),
                onCreateReport = {
                    navHostController.navigate(
                        route = Screens.CreateReportScreen.name
                    ) {
                        launchSingleTop = true
                    }
                },
                mainViewModel = mainViewModel
            )
        }

        /** Create Report Screen */
        composable(route = Screens.CreateReportScreen.name) {
            CreateReportScreen(
                modifier = Modifier.fillMaxSize(),
                onBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}