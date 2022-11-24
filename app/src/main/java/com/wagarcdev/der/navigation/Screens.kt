package com.wagarcdev.der.navigation

enum class Screens {
    MainScreen,
    AuthScreen,
    DetailScreen,
    ReportsScreen,
    CreateReportScreen;

    companion object {
        fun fromRoute(
            route: String?
        ): Screens = when (route?.substringBefore(delimiter = "/")) {
            MainScreen.name -> MainScreen
            AuthScreen.name -> AuthScreen
            DetailScreen.name -> DetailScreen
            ReportsScreen.name -> ReportsScreen
            CreateReportScreen.name -> CreateReportScreen
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route was not recognized")
        }
    }
}