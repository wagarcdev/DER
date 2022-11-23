package com.wagarcdev.der.presentation.screens.screen_create_report.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

private const val defaultAnimationDurationMillis = 200

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideOutRight(
    duration: Int = defaultAnimationDurationMillis
) = slideOutOfContainer(
    towards = AnimatedContentScope.SlideDirection.Right,
    animationSpec = tween(durationMillis = duration)
)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideOutLeft(
    duration: Int = defaultAnimationDurationMillis
) = slideOutOfContainer(
    towards = AnimatedContentScope.SlideDirection.Left,
    animationSpec = tween(durationMillis = duration)
)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideInRight(
    duration: Int = defaultAnimationDurationMillis
) = slideIntoContainer(
    towards = AnimatedContentScope.SlideDirection.Right,
    animationSpec = tween(durationMillis = duration)
)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideInLeft(
    duration: Int = defaultAnimationDurationMillis
) = slideIntoContainer(
    towards = AnimatedContentScope.SlideDirection.Left,
    animationSpec = tween(durationMillis = duration)
)

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composeSlideAnimateScreen(
    route: String,
    animationDuration: Int = defaultAnimationDurationMillis,
    content: @Composable () -> Unit
) = composable(
    route = route,
    enterTransition = { slideInLeft(duration = animationDuration) },
    exitTransition = { slideOutLeft(duration = animationDuration) },
    popEnterTransition = { slideInRight(duration = animationDuration) },
    popExitTransition = { slideOutRight(duration = animationDuration) },
    content = { content() }
)

fun NavController.navigate(destinationRoute: String) {
    navigate(route = destinationRoute) {
        launchSingleTop = true
    }
}

fun NavController.navigateBackWithFallback(
    currentRoute: String,
    destinationRoute: String
) {
    if (!popBackStack()) navigate(route = destinationRoute) {
        popUpTo(route = currentRoute) { inclusive = true }
        launchSingleTop = true
    }
}