package com.wagarcdev.der.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.wagarcdev.der.presentation.screens.screen_main.MainScreen
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AppNavigation() {


    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

    mainViewModel.navHostController = rememberNavController()
    val context = LocalContext.current

    val isUserSigned = false
    val isLogged = signInGoogleViewModel.checkIfIsLogged(context)




    NavHost(
        startDestination =
        if (isUserSigned) {
            Screens.MainScreen.name
        } else {
            Screens.AuthScreen.name
        },
        navController = mainViewModel.navHostController
    ) {

        /** Main Screen */
        composable(Screens.MainScreen.name) {
            MainScreen(mainViewModel,signInGoogleViewModel)
        }


        /** Authentication Screen */
        composable(Screens.AuthScreen.name) {
            AuthScreen(mainViewModel)
        }

        /** Contracts Screen */
        composable(Screens.DetailScreen.name) {
            ContractsScreen(mainViewModel)
        }

        /** Reports Screen */
        composable(Screens.ReportsScreen.name) {
            ReportsScreen(mainViewModel)
        }
    }

}
