package com.wagarcdev.der.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel
import com.wagarcdev.der.presentation.screens.screen_auth.AuthScreen
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsScreen

@Composable
fun AppNavigation() {


    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

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
        composable(Screens.MainScreen.name) {
            ContractsScreen(mainViewModel, signInGoogleViewModel)
        }

        /** Authentication Screen */
        composable(Screens.AuthScreen.name) {
            AuthScreen(mainViewModel, signInGoogleViewModel)
        }


        /** Reports Screen */
        composable(Screens.ReportsScreen.name) {
            ReportsScreen(mainViewModel)
        }
    }

}
