package com.wagarcdev.der.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel
import com.wagarcdev.der.presentation.screens.screen_auth.AuthScreen
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen
import com.wagarcdev.der.presentation.screens.screen_create_report.CreateReportScreen
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsScreen

@Composable
fun AppNavigation() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

    // nav host should not be stored on view model
    // TODO(remove nav controller from view model)
    mainViewModel.navHostController = rememberNavController()
    val context = LocalContext.current

//    val isUserSigned = false
    val isLogged = signInGoogleViewModel.checkIfIsLogged(context)

    NavHost(
        startDestination =
        if (isLogged) {
            Screens.MainScreen.name
        } else {
            Screens.AuthScreen.name
        },
        navController = mainViewModel.navHostController
    ) {
        /** Main Screen */
        composable(route = Screens.MainScreen.name) {
            ContractsScreen(mainViewModel, signInGoogleViewModel)
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
                    mainViewModel.navHostController.navigate(
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
                    mainViewModel.navHostController.popBackStack()
                }
            )
        }
    }
}