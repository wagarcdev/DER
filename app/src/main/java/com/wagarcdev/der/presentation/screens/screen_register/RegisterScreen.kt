package com.wagarcdev.der.presentation.screens.screen_register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wagarcdev.der.R
import com.wagarcdev.der.presentation.ui.components.BackgroundImageRow
import com.wagarcdev.der.presentation.ui.components.ClearTrailingButton
import com.wagarcdev.der.presentation.ui.components.SignUpButton
import com.wagarcdev.der.presentation.ui.components.TempDerOutlinedTextField
import com.wagarcdev.der.presentation.ui.components.ToggleTextVisibilityTrailingButton
import com.wagarcdev.der.presentation.ui.theme.DER_yellow

/**
 * Compose the Register Screen.
 *
 * @param modifier the [Modifier] to be applied on main container of this screen.
 * @param onNavigateBack callback to navigate back from this screen.
 * @param onNavigateToContractsScreen callback to navigate to contracts screen.
 * @param viewModel the [RegisterViewModel]. Default is provided by [hiltViewModel].
 */
@Composable
fun RegisterScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToContractsScreen: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val screenState by viewModel.registerState.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current
    val nextKeyBoardAction = KeyboardActions(
        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { channel ->
            when (channel) {
                RegisterChannel.UnavailableEmail -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "This email already has an account associated"
                    )
                }
                RegisterChannel.RegisterFailed -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Something went wrong"
                    )
                }
                RegisterChannel.RegisterSuccessfully -> onNavigateToContractsScreen()
            }
        }
    }

    BackHandler(onBack = onNavigateBack)

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState
    ) { innerPadding ->
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TempDerOutlinedTextField(
                        value = screenState.name,
                        onValueChange = { viewModel.changeName(value = it) },
                        labelString = "Full name",
                        errorMessage = screenState.nameError,
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Person, contentDescription = null)
                        },
                        trailingIcon = if (screenState.name.isEmpty()) null else {
                            { ClearTrailingButton { viewModel.changeName(value = "") } }
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = nextKeyBoardAction
                    )

                    TempDerOutlinedTextField(
                        value = screenState.email,
                        onValueChange = { viewModel.changeEmail(value = it) },
                        labelString = "Email",
                        errorMessage = screenState.emailError,
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Email, contentDescription = null)
                        },
                        trailingIcon = if (screenState.email.isEmpty()) null else {
                            { ClearTrailingButton { viewModel.changeEmail(value = "") } }
                        },
                        enableWhiteSpace = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = nextKeyBoardAction
                    )

                    TempDerOutlinedTextField(
                        value = screenState.password,
                        onValueChange = { viewModel.changePassword(value = it) },
                        labelString = "Password",
                        errorMessage = screenState.passwordError,
                        enableWhiteSpace = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Password, contentDescription = null)
                        },
                        trailingIcon = {
                            ToggleTextVisibilityTrailingButton(
                                onClick = viewModel::togglePasswordVisibility,
                                isVisible = screenState.isPasswordVisible
                            )
                        },
                        hideText = !screenState.isPasswordVisible,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = nextKeyBoardAction
                    )

                    TempDerOutlinedTextField(
                        value = screenState.repeatedPassword,
                        onValueChange = { viewModel.changeRepeatedPassword(value = it) },
                        labelString = "Repeat password",
                        errorMessage = screenState.repeatedPasswordError,
                        enableWhiteSpace = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Password, contentDescription = null)
                        },
                        trailingIcon = {
                            ToggleTextVisibilityTrailingButton(
                                onClick = viewModel::toggleRepeatedPasswordVisibility,
                                isVisible = screenState.isRepeatedPasswordVisible
                            )
                        },
                        hideText = !screenState.isRepeatedPasswordVisible,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 16.dp))

            SignUpButton(
                onClick = viewModel::signUpForEmailAndPassword,
                buttonText = "Cadastrar"
            )

            Row(
                modifier = Modifier.padding(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Já possui cadastro?")

                Text(
                    modifier = Modifier.clickable { onNavigateBack() },
                    text = "Efetue o seu Login!",
                    color = DER_yellow,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            }

            Image(
                modifier = Modifier.fillMaxWidth(fraction = 0.5F),
                painter = painterResource(id = R.drawable.logotipo_st),
                contentDescription = stringResource(R.string.Logo_ST6),
                contentScale = ContentScale.Fit
            )
        }
    }
}