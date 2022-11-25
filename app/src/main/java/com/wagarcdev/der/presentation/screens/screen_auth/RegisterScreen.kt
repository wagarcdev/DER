package com.wagarcdev.der.presentation.screens.screen_auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.R
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.presentation.navigation.graphs.AuthScreens
import com.wagarcdev.der.presentation.ui.components.BackgroundImageRow
import com.wagarcdev.der.presentation.ui.components.InputField
import com.wagarcdev.der.presentation.ui.components.SignUpButton
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirm = remember { mutableStateOf("") }



    Surface(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .navigationBarsPadding()
            .padding(bottom = 16.dp),

        ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(
                    text = "Novo cadastro",
                    color = Color.Black,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.alpha(1f),
                    text = stringResource(R.string.registro_frase),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(contentAlignment = Alignment.Center) {

                BackgroundImageRow(imageResInt = R.drawable.backgroung_gray_img)

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1.0f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val isFullnameError = remember {
                                mutableStateOf(false)
                            }
                            val isEmailError = remember {
                                mutableStateOf(false)
                            }

                            val isUsernameError = remember {
                                mutableStateOf(false)
                            }
                            val isPasswordError = remember {
                                mutableStateOf(false)
                            }

                            val isPasswordConfirmError = remember {
                                mutableStateOf(false)
                            }


                            val localFocusManager = LocalFocusManager.current
                            val focusRequester = FocusRequester()

                            InputField(
                                modifier = Modifier.onFocusChanged {
                                    if (it.isFocused) {
                                        isFullnameError.value = false
                                    }
                                },
                                valueState = fullName,
                                labelId = "Nome completo",
                                enabled = true,
                                isSingleLine = true,
                                isError = isFullnameError,
                                errorMessage = "Preencha seu nome completo",
                                focusRequester = focusRequester
                            )

                            InputField(
                                valueState = email,
                                labelId = "E-mail",
                                enabled = true,
                                isSingleLine = true,
                                isError = isEmailError,
                                errorMessage = "Preencha o email",
                                focusRequester = focusRequester
                            )

                            InputField(
                                valueState = username,
                                labelId = "Usuário",
                                enabled = true,
                                isSingleLine = true,
                                isError = isUsernameError,
                                errorMessage = "Preencha o nome de usuario",
                                focusRequester = focusRequester
                            )

                            InputField(
                                valueState = password,
                                labelId = "Senha",
                                enabled = true,
                                isSingleLine = true,
                                isPassword = true,
                                isError = isPasswordError,
                                errorMessage = "Preencha a senha",
                                focusRequester = focusRequester
                            )

                            InputField(
                                valueState = passwordConfirm,
                                labelId = "Repita a senha",
                                enabled = true,
                                isSingleLine = true,
                                isPassword = true,
                                isError = isPasswordConfirmError,
                                errorMessage = "Preencha a confirmação da senha",
                                focusRequester = focusRequester
                            )

                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val validState = remember(
                    fullName.value,
                    username.value,
                    email.value,
                    password.value,
                    passwordConfirm.value
                ) {
                    fullName.value.trim().isNotEmpty() && username.value.trim()
                        .isNotEmpty()
                            && email.value.trim().isNotEmpty() && password.value.trim()
                        .isNotEmpty() && passwordConfirm.value.trim()
                        .isNotEmpty()
                }

                SignUpButton(
                    onClick = {
                        coroutineScope.launch {
                            mainViewModel.createSimpleUser(username, fullName, email, password, passwordConfirm, context, navHostController)
                        }

                    },
                    enable = validState,
                    buttonText = "Cadastrar"
                )

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 32.dp)
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End

                ) {
                    Text(
                        text = "Já possui cadastro? ",
                        color = Color.Black,
                        fontSize = 13.sp
                    )

                    Text(
                        modifier = Modifier
                            .clickable {
                                       navHostController
                                           .navigate(AuthScreens.Login.route)
                            },
                        text = "Efetue o seu Login!",
                        color = DER_yellow,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Image(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    painter = painterResource(id = R.drawable.logotipo_st),
                    contentDescription = stringResource(R.string.Logo_ST6),
                    contentScale = ContentScale.Fit

                )
            }
        }
    }
}