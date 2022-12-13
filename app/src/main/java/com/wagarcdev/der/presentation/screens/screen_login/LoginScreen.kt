package com.wagarcdev.der.presentation.screens.screen_login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wagarcdev.der.R
import com.wagarcdev.der.presentation.screens.screen_login.google_one_tap_sign_in.GoogleOneTapSignIn
import com.wagarcdev.der.presentation.screens.screen_login.google_one_tap_sign_in.rememberOneTapSignInState
import com.wagarcdev.der.presentation.ui.components.BackgroundImageRow
import com.wagarcdev.der.presentation.ui.components.ClearTrailingButton
import com.wagarcdev.der.presentation.ui.components.SignInButton
import com.wagarcdev.der.presentation.ui.components.SignUpButton
import com.wagarcdev.der.presentation.ui.components.TempDerOutlinedTextField
import com.wagarcdev.der.presentation.ui.components.ToggleTextVisibilityTrailingButton
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_intense
import com.wagarcdev.der.presentation.ui.theme.RB_Black
import com.wagarcdev.der.presentation.ui.theme.RB_White

/**
 * Compose the Login Screen.
 *
 * @param modifier the [Modifier] to be applied on main container of this screen.
 * @param onNavigateBack callback to navigate back from this screen.
 * @param onNavigateToRegisterScreen callback to navigate to register screen.
 * @param viewModel the [LoginViewModel]. Default is provided by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToRegisterScreen: () -> Unit,
    onNavigateToContractsScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val screenState by viewModel.loginState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    val oneTapSignState = rememberOneTapSignInState()
    GoogleOneTapSignIn(
        state = oneTapSignState,
        clientId = stringResource(id = R.string.google_cloud_server_client_id),
        onUserReceived = { user ->
            viewModel.signInWithGoogleAccount(user = user)
        },
        onDialogDismissed = { message ->
            // todo show the message in a proper way
            println(message)
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { channel ->
            when (channel) {
                LoginChannel.EmailNotFound -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "This email is not associated with an account"
                    )
                }
                LoginChannel.IncorrectPassword -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Incorrect password"
                    )
                }
                LoginChannel.SignInWithGoogleFailed -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Something went wrong with Google Sign-In"
                    )
                }
                LoginChannel.SignInSuccessfully -> onNavigateToContractsScreen()
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
            Image(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.85F)
                    .fillMaxHeight(fraction = 0.2F),
                painter = painterResource(id = R.drawable.logotipo_st),
                contentDescription = stringResource(R.string.DER_Logo_desc)
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BackgroundImageRow(imageResInt = R.drawable.backgroung_color_img)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.align(alignment = Alignment.Start),
                        text = "Olá!",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.align(alignment = Alignment.Start),
                        text = stringResource(R.string.saudacao_frase),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(height = 8.dp))

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
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                        )
                    )

                    Spacer(modifier = Modifier.height(height = 4.dp))

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
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    SignUpButton(
                        onClick = viewModel::signInWithEmailAndPassword,
                        buttonText = "Entrar"
                    )

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    Text(
                        text = "Ou então, entre com um clique pelo Google",
                        fontWeight = FontWeight.SemiBold
                    )

                    SignInButton(
                        buttonFillMaxWidthFloat = 0.7f,
                        buttonVerticalPaddingDp = 15.dp,
                        buttonDefaultMinHeight = 30.dp,
                        rowVerticalPadding = 1.dp,
                        rowHorizontalPadding = 8.dp,
                        isLoading = oneTapSignState.opened,
                        progressColor = DER_yellow_intense,
                        iconID = R.drawable.ic_google_logo,
                        text = "Login usando Google",
                        iconDescription = "Google Icon",
                        textAlign = TextAlign.Center,
                        textFillMaxWidthFloat = 0.85f,
                        textFontColor = RB_Black,
                        fontWeight = FontWeight.SemiBold,
                        borderStroke = BorderStroke(0.dp, Color.Transparent),
                        leftGradientColor = RB_White,
                        centerLeftGradientColor = RB_White,
                        centerGradientColor = RB_White,
                        centerRightGradientColor = RB_White,
                        rightGradientColor = RB_White,
                        bottomSpacer = 0.dp,
                    ) {
                        oneTapSignState.open()
                    }
                }
            }

            Row(
                modifier = Modifier.padding(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Ainda não tem cadastro?")

                Text(
                    modifier = Modifier.clickable { onNavigateToRegisterScreen() },
                    text = "CADASTRE-SE AQUI!",
                    color = DER_yellow,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5F)
                    .fillMaxHeight(fraction = 0.6F),
                painter = painterResource(id = R.drawable.logo_der_sp_horizontal),
                contentDescription = stringResource(R.string.Logo_ST6),
                contentScale = ContentScale.Fit
            )
        }
    }
}