package com.wagarcdev.der.presentation.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.wagarcdev.der.presentation.navigation.Graph
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen
import com.wagarcdev.der.presentation.screens.screen_create_report.CreateReportScreen
import com.wagarcdev.der.presentation.screens.screen_reports.ReportsScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.appNavGraph(
    onNavigateBack: () -> Unit,
    navHostController: NavHostController
) {
    navigation(
        route = Graph.APP,
        startDestination = AppScreens.Contracts.route
    ) {
        composable(route = AppScreens.Contracts.route) {
            ContractsScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = onNavigateBack,
                onNavigateToLoginScreen = {
                    navHostController.navigate(route = AuthScreens.Login.route) {
                        popUpTo(route = AppScreens.Contracts.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToReportsScreen = {
                    navHostController.navigate(route = AppScreens.Reports.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AppScreens.Reports.route) {
            ReportsScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = { navHostController.popBackStack() },
                onNavigateToCreateReportScreen = {
                    navHostController.navigate(route = AppScreens.CreateReport.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AppScreens.CreateReport.route) {
            CreateReportScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = { navHostController.popBackStack() }
            )
        }
    }
}


sealed class AppScreens(val route: String) {
    object Contracts : AppScreens(route = "CONTRACTS")
    object Reports : AppScreens(route = "REPORTS")
    object CreateReport : AppScreens(route = "CREATE_REPORT")
}