package com.wagarcdev.der.presentation.screens.screen_auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.R
import com.wagarcdev.der.SignInGoogleViewModel
import com.wagarcdev.der.google.GoogleApiContract
import com.wagarcdev.der.presentation.ui.components.BackgroundImageRow
import com.wagarcdev.der.presentation.ui.components.InputField
import com.wagarcdev.der.presentation.ui.components.SignInButton
import com.wagarcdev.der.presentation.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginContent(
    mainViewModel: MainViewModel,
    wannaRegister: MutableState<Boolean>,
    signInGoogleViewModel: SignInGoogleViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var isLoading = remember { mutableStateOf(false) }

    val signInRequestCode = 0
    val context = LocalContext.current


    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            signInGoogleViewModel.googleSignIn(task, context, mainViewModel)
        }

    LazyColumn(
        modifier = Modifier
            .imePadding()
            .systemBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.2f),
                painter = painterResource(id = R.drawable.logotipo_st),
                contentDescription = stringResource(R.string.DER_Logo_desc)
            )
        }
        item {
            Box(contentAlignment = Alignment.Center) {

                BackgroundImageRow(imageResInt = R.drawable.backgroung_color_img)

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .fillMaxWidth(1f),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Olá!",
                                    color = Color.Black,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(top = 2.dp, bottom = 2.dp)
                                    .fillMaxWidth(1f),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    modifier = Modifier.alpha(1f),
                                    text = stringResource(R.string.saudacao_frase),
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1.0f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            val isEmailError = remember {
                                mutableStateOf(false)
                            }
                            val isPasswordError = remember {
                                mutableStateOf(false)
                            }

                            val localFocusManager = LocalFocusManager.current
                            val focusRequester = FocusRequester()

                            InputField(
                                modifier = Modifier.onFocusChanged {
                                    if (it.isFocused) {
                                        isEmailError.value = false
                                    }
                                },
                                valueState = username,
                                labelId = "Usuário",
                                enabled = true,
                                isSingleLine = true,
                                isError = isEmailError,
                                errorMessage = "Preencha o nome de usuario",
                                focusRequester = focusRequester,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Person,
                                        "Usuário"
                                    )
                                }
                            )

                            InputField(
                                modifier = Modifier.onFocusChanged {
                                    if (it.isFocused) {
                                        isPasswordError.value = false
                                    }
                                },
                                valueState = password,
                                labelId = "Senha",
                                enabled = true,
                                isSingleLine = true,
                                focusRequester = focusRequester,
                                isError = isPasswordError,
                                errorMessage = "Preencha a senha",
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Key,
                                        "Senha"
                                    )
                                },
                                keyboardType = KeyboardType.Password,
                                isPassword = true,
                                imeAction = ImeAction.Done
                            )

                            Row(
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End

                            ) {
                                Text(
                                    text = "Esqueceu sua senha?  Recupere-a ",
                                    color = Color.Black,
                                    fontSize = 13.sp
                                )

                                Text(
                                    modifier = Modifier
                                        .clickable {
                                            coroutineScope.launch {
                                                /** TODO onCLick */
                                            }
                                        },
                                    text = "clicando aqui!",
                                    color = Color.DarkGray,
                                    textDecoration = TextDecoration.Underline,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            //Button "Entrar"
                            Card(
                                modifier = Modifier
                                    .height(48.dp)
                                    .width(132.dp)
                                    .clickable {
                                        if (username.value.isEmpty()) {
                                            isEmailError.value = true
                                            localFocusManager.clearFocus()
                                        } else if (password.value.isEmpty()) {
                                            isPasswordError.value = true
                                            localFocusManager.clearFocus()
                                        } else {
                                            coroutineScope.launch {
                                                mainViewModel.signInUser(
                                                    password,
                                                    username,
                                                    mainViewModel,
                                                    context
                                                )
                                            }
                                        }
                                    }
                                    .clip(RoundedCornerShape(15.dp))
                                    .shadow(2.dp)
                                    .background(
                                        Brush
                                            .verticalGradient(
                                                listOf(
                                                    DER_yellow_light_extra,
                                                    DER_yellow_light,
                                                    DER_yellow,
                                                    DER_yellow,
                                                    DER_yellow_intense
                                                )
                                            )
                                    ),
                                backgroundColor = Color(0x00000000),
                                elevation = 0.dp,
                                shape = RoundedCornerShape(15.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Entrar",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        color = Color.Black
                                    )
                                }

                            }

                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp, bottom = 2.dp)
                                    .fillMaxWidth(1f),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier.alpha(1f),
                                    text = "ou então, faça Login com um clique pelo Google",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            /** Sign In Google Button*/
                            SignInButton(
                                buttonFillMaxWidthFloat = 0.7f,
                                buttonVerticalPaddingDp = 15.dp,
                                buttonDefaultMinHeight = 30.dp,
                                rowVerticalPadding = 1.dp,
                                rowHorizontalPadding = 8.dp,
                                isLoading = false,
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
                                authResultLauncher.launch(signInRequestCode)
                            }


                        }
                    }
                }

            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End

            ) {
                Text(
                    text = "Ainda não tem cadastro?   ",
                    color = Color.Black,
                    fontSize = 13.sp
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                wannaRegister.value = true
                            }
                        },
                    text = "CADASTRE-SE AQUI!",
                    color = DER_yellow_intense,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
        item {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.6f),
                painter = painterResource(id = R.drawable.logo_der_sp_horizontal),
                contentDescription = stringResource(R.string.Logo_ST6),
                contentScale = ContentScale.Fit

            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AuthScreenContentPreview() {

    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

    LoginContent(
        mainViewModel = mainViewModel,
        wannaRegister = remember { mutableStateOf(false) },
        signInGoogleViewModel = signInGoogleViewModel
    )
}
