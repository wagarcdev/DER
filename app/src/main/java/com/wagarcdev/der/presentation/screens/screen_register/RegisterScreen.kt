package com.wagarcdev.der.presentation.screens.screen_register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wagarcdev.der.R
import com.wagarcdev.der.presentation.ui.components.BackgroundImageRow
import com.wagarcdev.der.presentation.ui.components.SignUpButton

/**
 * Compose the Register Screen.
 *
 * @param modifier the [Modifier] to be applied on main container of this screen.
 * @param onNavigateBack callback to navigate back from this screen.
 * @param viewModel the [RegisterViewModel]. Default is provided by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RegisterScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val screenState by viewModel.registerState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    BackHandler(onBack = onNavigateBack)

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Novo cadastro",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.registro_frase),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BackgroundImageRow(imageResInt = R.drawable.backgroung_gray_img)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = screenState.name,
                        onValueChange = { viewModel.changeName(value = it) },
                        label = { Text(text = "Full name") }
                    )

                    TextField(
                        value = screenState.email,
                        onValueChange = { viewModel.changeEmail(value = it) },
                        label = { Text(text = "Email") }
                    )

                    TextField(
                        value = screenState.username,
                        onValueChange = { viewModel.changeUsername(value = it) },
                        label = { Text(text = "Username") }
                    )

                    TextField(
                        value = screenState.password,
                        onValueChange = { viewModel.changePassword(value = it) },
                        label = { Text(text = "Password") }
                    )

                    TextField(
                        value = screenState.repeatedPassword,
                        onValueChange = { viewModel.changeRepeatedPassword(value = it) },
                        label = { Text(text = "Repeat password") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 16.dp))

            SignUpButton(
                onClick = { /* todo */ },
                buttonText = "Cadastrar"
            )
        }
    }
}