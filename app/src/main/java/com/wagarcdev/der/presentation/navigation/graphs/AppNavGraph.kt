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
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.reportIdKey
import com.wagarcdev.der.presentation.screens.screen_contracts.ContractsScreen
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.CreateOrEditReportScreen
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
                onNavigateToCreateReportScreen = { contractId ->
                    navHostController.navigate(
                        route = AppScreens.CreateOrEditReport.routeWithContractId(id = contractId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onNavigateToEditReportScreen = { reportId ->
                    navHostController.navigate(
                        route = AppScreens.CreateOrEditReport.routeWithReportId(id = reportId)
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // todo maybe there is a better way to handler multiple navigation args
        // or maybe can be better split this screen into two, one to create and one to edit
        // for now, we have two optional args, but one of them should be always passed
        // if contract id is the one, indicates that we should create a new report
        // if report id is the one, indicates that we should edit the current report
        composable(
            route = AppScreens.CreateOrEditReport.route,
            arguments = listOf(
                navArgument(name = contractIdKey) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(name = reportIdKey) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            CreateOrEditReportScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = { navHostController.popBackStack() }
            )
        }
    }
}


sealed class AppScreens(val route: String) {
    val contractIdKey = "contractId"
    val reportIdKey = "reportId"

    object Contracts : AppScreens(route = "contracts_screen")

    object Reports : AppScreens(
        route = "reports_screen?$contractIdKey={$contractIdKey}"
    ) {
        fun routeWithContractId(id: String): String = route.replace(
            oldValue = "{$contractIdKey}",
            newValue = id
        )
    }

    object CreateOrEditReport : AppScreens(
        route = "create_edit_report_screen?$contractIdKey={$contractIdKey}&$reportIdKey={$reportIdKey}"
    ) {
        fun routeWithContractId(id: String): String = route.replace(
            oldValue = "{$contractIdKey}",
            newValue = id
        )

        fun routeWithReportId(id: String): String = route.replace(
            oldValue = "{$reportIdKey}",
            newValue = id
        )
    }
}