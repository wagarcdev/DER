package com.wagarcdev.der.presentation.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.wagarcdev.der.presentation.navigation.Graph
import com.wagarcdev.der.presentation.screens.screen_auth.LoginScreen
import com.wagarcdev.der.presentation.screens.screen_auth.RecoverScreen
import com.wagarcdev.der.presentation.screens.screen_auth.RegisterScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {



    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreens.Login.route
    ) {

//        composable(AuthScreens.Splash.route) {
//            AnimatedSplashScreen(navHostController = navHostController)
//        }

        composable(AuthScreens.Login.route) {
            LoginScreen(navHostController)
        }

        composable(AuthScreens.Register.route) {
            RegisterScreen(navHostController = navHostController)
        }

        composable(AuthScreens.Recover.route) {
            RecoverScreen(navHostController = navHostController)
        }


    }
}


sealed class AuthScreens(val route: String) {
    object Splash : AuthScreens(route = "SPLASH")
    object Register : AuthScreens(route = "REGISTER")
    object Login : AuthScreens(route = "LOGIN")
    object Recover : AuthScreens(route = "RECOVER")
}