package com.wagarcdev.der.presentation.screens.screen_register

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.domain.model.SimpleUser
import com.wagarcdev.der.navigation.Screens
import com.wagarcdev.der.presentation.screens.screen_main.BackgroundImageRow
import com.wagarcdev.der.ui.theme.DER_yellow
import com.wagarcdev.der.ui.theme.DER_yellow_intense
import com.wagarcdev.der.ui.theme.DER_yellow_light
import com.wagarcdev.der.ui.theme.DER_yellow_light_extra
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun RegisterScreenContent(
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
            val isEqual = checkIfPasswordAreEquals()
            if (isEqual) {
                coroutineScope.launch {
                    val simpleUser =
                        SimpleUser(0, username.value, fullName.value, email.value, password.value)
                    mainViewModel.createNewSimpleUser(simpleUser)
                }

            }
        } else {
            Toast.makeText(context, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT)
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

                BackgroundImageRow(imageResInt = com.wagarcdev.der.R.drawable.backgroung_gray_img)

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

                            OutlinedTextField(
                                value = fullName.value,
                                shape = RoundedCornerShape(15.dp),
                                onValueChange = {
                                    coroutineScope.launch {
                                        fullName.value = it
                                    }
                                },
                                label = { Text(text = "Nome completo", color = Color.Gray) },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    focusedIndicatorColor = DER_yellow,
                                    unfocusedLabelColor = Color.Black,
                                    backgroundColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = email.value,
                                shape = RoundedCornerShape(15.dp),
                                onValueChange = {
                                    coroutineScope.launch {
                                        email.value = it
                                    }
                                },
                                label = { Text(text = "E-mail", color = Color.Gray) },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    focusedIndicatorColor = DER_yellow,
                                    unfocusedLabelColor = Color.Black,
                                    backgroundColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = username.value,
                                shape = RoundedCornerShape(15.dp),
                                onValueChange = {
                                    coroutineScope.launch {
                                        username.value = it
                                    }
                                },
                                label = { Text(text = "Usuário", color = Color.Gray) },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    focusedIndicatorColor = DER_yellow,
                                    unfocusedLabelColor = Color.Black,
                                    backgroundColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = password.value,
                                shape = RoundedCornerShape(15.dp),
                                onValueChange = {
                                    coroutineScope.launch {
                                        password.value = it
                                    }
                                },
                                label = { Text(text = "Senha", color = Color.Gray) },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    focusedIndicatorColor = DER_yellow,
                                    unfocusedLabelColor = Color.Black,
                                    backgroundColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = passwordConfirm.value,
                                shape = RoundedCornerShape(15.dp),
                                onValueChange = {
                                    coroutineScope.launch {
                                        passwordConfirm.value = it
                                    }
                                },
                                label = { Text(text = "Repita a senha", color = Color.Gray) },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    focusedIndicatorColor = DER_yellow,
                                    unfocusedLabelColor = Color.Black,
                                    backgroundColor = Color.White
                                )
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
                            mainViewModel.navHostController
                                .navigate(Screens.AuthScreen.name)
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

@RequiresApi(Build.VERSION_CODES.R)
@Composable
@Preview(showBackground = true)
fun RegisterScreenContentPreview() {

    val mainViewModel: MainViewModel = hiltViewModel()

    RegisterScreenContent(
        mainViewModel = mainViewModel,
        wannaRegister = remember { mutableStateOf(false) }
    )
}




