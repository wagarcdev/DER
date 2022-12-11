package com.wagarcdev.der.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.wagarcdev.der.presentation.navigation.graphs.appNavGraph
import com.wagarcdev.der.presentation.navigation.graphs.authNavGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    startDestination: String,
    onNavigateBack: () -> Unit
) {
    AnimatedNavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = startDestination
    ) {
        authNavGraph(
            onNavigateBack = onNavigateBack,
            navHostController = navHostController
        )

        appNavGraph(
            onNavigateBack = onNavigateBack,
            navHostController = navHostController
        )
    }
}


object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val APP = "app_graph"
}