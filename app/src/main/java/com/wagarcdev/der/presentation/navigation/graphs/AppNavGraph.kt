package com.wagarcdev.der.presentation.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.wagarcdev.der.presentation.navigation.Graph
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.appNavGraph(navHostController: NavHostController) {


    navigation(
        route = Graph.APP,
        startDestination = AppScreens.Contracts.route
    ) {

        composable(AppScreens.Contracts.route) {
            ContractsScreen(navHostController)
        }

    }

}


sealed class AppScreens(val route: String) {
    object Contracts : AppScreens(route = "CONTRACTS")
    object Reports : AppScreens(route = "REPORTS")
    object CreateReport : AppScreens(route = "CREATE_REPORT")
}