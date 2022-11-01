package com.wagarcdev.der.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wagarcdev.compose_mvvm_empty_project.navigation.Screens
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.presentation.screens.screen_auth.AuthScreen
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen
import com.wagarcdev.der.presentation.screens.screen_main.MainScreen
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AppNavigation() {


    val mainViewModel: MainViewModel = hiltViewModel()

    mainViewModel.navHostController = rememberNavController()

    val isUserSigned =  false
//        signInGoogleViewModel.checkSignedInUser(context) TODO implement Firebase Auth




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
        composable(Screens.MainScreen.name){
            MainScreen(mainViewModel)
        }


        /** Authentication Screen */
        composable(Screens.AuthScreen.name){
            AuthScreen(mainViewModel)
        }

        /** Contracts Screen */
        composable(Screens.DetailScreen.name){
            ContractsScreen(mainViewModel)
        }

        /** Reports Screen */
        composable(Screens.ReportsScreen.name){
            ReportsScreen(mainViewModel)
        }
    }

}
