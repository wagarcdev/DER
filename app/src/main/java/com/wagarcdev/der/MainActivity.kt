package com.wagarcdev.der

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wagarcdev.der.navigation.AppNavigation
import com.wagarcdev.der.presentation.ui.theme.DERTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition { mainViewModel.isLoading.value }
        }

        setContent {
            DERTheme {
                AppNavigation()
            }
        }
    }
}

//comentei para poder passar o contexto pra logar, não consegui por ele dentro do método default preview
 /**contexto � sempre local, alterei aqui e no Navigation, o contexto se passa dentro do Composable,
 no caso, dentro da MainScreen, caso contr�rio pode gerar MemoryLeak */



@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DERTheme {
        AppNavigation()
    }
}

