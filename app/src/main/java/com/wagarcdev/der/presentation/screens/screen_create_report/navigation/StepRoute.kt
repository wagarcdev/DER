package com.wagarcdev.der.presentation.screens.screen_create_report.navigation

sealed class StepRoute(val route: String) {
    object FirstStep : StepRoute(route = "first_step")
    object SecondStep : StepRoute(route = "third_step")
}