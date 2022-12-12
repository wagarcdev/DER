package com.wagarcdev.der.presentation.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.wagarcdev.der.presentation.navigation.Graph
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.contractIdKey
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
                onNavigateToReportsScreen = { contractId ->
                    navHostController.navigate(
                        route = AppScreens.Reports.routeWithContractId(id = contractId)
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = AppScreens.Reports.route,
            arguments = listOf(
                navArgument(name = contractIdKey) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
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
    val contractIdKey: String = "contractId"

    object Contracts : AppScreens(route = "contracts_screen")
    object CreateReport : AppScreens(route = "create_report_screen")

    object Reports : AppScreens(route = "reports_screen?$contractIdKey={$contractIdKey}") {
        fun routeWithContractId(id: String): String = route.replace(
            oldValue = "{$contractIdKey}",
            newValue = id
        )
    }
}