package com.wagarcdev.der.presentation.screens.screen_auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.R
import com.wagarcdev.der.presentation.ui.components.InputField
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.navigation.Screens
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_intense
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_light
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_light_extra
import com.wagarcdev.der.presentation.ui.components.BackgroundImageRow
import kotlinx.coroutines.launch

@Composable
fun RegisterContent(
    mainViewModel: MainViewModel,
    wannaRegister: MutableState<Boolean>,
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirm = remember { mutableStateOf("") }


    fun checkIfPasswordAreEquals(): Boolean {
        if (password.value == passwordConfirm.value) {
            return true
        }
        return false
    }

    fun createSimpleUser() {
        if (username.value.isNotEmpty() && fullName.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && passwordConfirm.value.isNotEmpty()) {
            if (email.value.contains("@")) {
                val isEqualsPassword = checkIfPasswordAreEquals()
                if (isEqualsPassword) {
                    coroutineScope.launch {
                        val simpleUser =
                            User(
                                System.currentTimeMillis().toString(),
                                email.value,
                                username.value,
                                fullName.value,
                                fullName.value,
                                "",
                                password.value,
                                true
                            )
                        mainViewModel.createNewSimpleUser(simpleUser).also {
                            mainViewModel.navHostController
                                .navigate(Screens.AuthScreen.name)
                        }

                    }
                } else {
                    Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Email não está de acordo", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Preencha todos os campos corretamente", Toast.LENGTH_LONG)
                .show()
        }


    }

    LazyColumn(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .wrapContentHeight(),
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
                            text = "Novo cadastro",
                            color = Color.Black,
                            fontSize = 42.sp,
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
                            text = stringResource(com.wagarcdev.der.R.string.registro_frase),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

        }
        item {
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
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .height(48.dp)
                        .width(132.dp)
                        .clickable {
                            createSimpleUser()


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
                            text = "Cadastrar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.Black
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
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
                                coroutineScope.launch {
                                    wannaRegister.value = false
                                }
                            },
                        text = "Efetue o seu Login!",
                        color = DER_yellow,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    painter = painterResource(id = com.wagarcdev.der.R.drawable.logotipo_st),
                    contentDescription = stringResource(com.wagarcdev.der.R.string.Logo_ST6),
                    contentScale = ContentScale.Fit

                )
            }
        }


    }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenContentPreview() {

    val mainViewModel: MainViewModel = hiltViewModel()

    RegisterContent(
        mainViewModel = mainViewModel,
        wannaRegister = remember { mutableStateOf(false) }
    )

}





