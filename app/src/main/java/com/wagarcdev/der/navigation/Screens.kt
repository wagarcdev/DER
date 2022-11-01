package com.wagarcdev.compose_mvvm_empty_project.navigation

enum class Screens {

    MainScreen,
    AuthScreen,
    ReportsScreen,
    DetailScreen;

    companion object {
        fun fromRoute(route: String?): Screens
                = when (route?.substringBefore("/")) {
            MainScreen.name -> MainScreen
            AuthScreen.name -> AuthScreen
            DetailScreen.name -> DetailScreen
            ReportsScreen.name -> ReportsScreen
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route was not recognized")
        }
    }
}