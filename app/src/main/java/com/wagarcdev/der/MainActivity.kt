package com.wagarcdev.der

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wagarcdev.der.presentation.navigation.Graph
import com.wagarcdev.der.presentation.navigation.RootNavGraph
import com.wagarcdev.der.presentation.ui.theme.DERTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var activityState: MainActivityState by mutableStateOf(
            value = MainActivityState.Loading
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.mainActivityState.onEach { state ->
                    activityState = state
                }.collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (activityState) {
                MainActivityState.Loading -> true
                is MainActivityState.Success -> false
            }
        }

        setContent {
            if (activityState is MainActivityState.Success) DERTheme {
                RootNavGraph(
                    navHostController = rememberAnimatedNavController(),
                    startDestination = startDestination(
                        currentUserId = (activityState as MainActivityState.Success).currentUserId
                    ),
                    onNavigateBack = { moveTaskToBack(true) }
                )
            }
        }
    }

    // todo find a better way to handler start destination

//    private fun startDestination(
//        activityState: MainActivityState
//    ): String = when (activityState) {
//        MainActivityState.Loading -> Graph.APP
//        is MainActivityState.Success -> {
//            if (activityState.currentUserId > 0) Graph.APP else Graph.AUTH
//        }
//    }

    private fun startDestination(currentUserId: Int): String =
        if (currentUserId > 0) Graph.APP else Graph.AUTH
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DERTheme {
        RootNavGraph(
            navHostController = rememberAnimatedNavController(),
            startDestination = Graph.AUTH,
            onNavigateBack = {}
        )
    }
}

