package com.wagarcdev.der.presentation.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.wagarcdev.der.presentation.navigation.Graph
import com.wagarcdev.der.presentation.screens.screen_login.LoginScreen
import com.wagarcdev.der.presentation.screens.screen_register.RegisterScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(
    onNavigateBack: () -> Unit,
    navHostController: NavHostController
) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreens.Login.route
    ) {
        composable(route = AuthScreens.Login.route) {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = onNavigateBack,
                onNavigateToRegisterScreen = {
                    navHostController.navigate(route = AuthScreens.Register.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToContractsScreen = {
                    navHostController.navigate(route = Graph.APP) {
                        popUpTo(route = AuthScreens.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AuthScreens.Register.route) {
            RegisterScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = { navHostController.popBackStack() },
                onNavigateToContractsScreen = {
                    navHostController.navigate(route = Graph.APP) {
                        popUpTo(route = AuthScreens.Register.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AuthScreens.Recover.route) {
            // todo
        }
    }
}

sealed class AuthScreens(val route: String) {
    object Login : AuthScreens(route = "LOGIN")
    object Register : AuthScreens(route = "REGISTER")
    object Recover : AuthScreens(route = "RECOVER")
}