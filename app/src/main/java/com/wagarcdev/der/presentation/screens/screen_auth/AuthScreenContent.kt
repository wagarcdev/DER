package com.wagarcdev.der.presentation.screens.screen_auth

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.wagarcdev.compose_mvvm_empty_project.navigation.Screens
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.R
import com.wagarcdev.der.google.AuthResultContract
import com.wagarcdev.der.presentation.screens.screen_main.BackgroundImageRow
import com.wagarcdev.der.ui.theme.*
import com.wagarcdev.der.ui.widgets.SignInButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AuthScreenContent(
    mainViewModel: MainViewModel,
    wannaRegister: MutableState<Boolean>,
) {

    val coroutineScope = rememberCoroutineScope()

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val isLoadingState =
        remember { mutableStateOf<Boolean?>(false) }
    val signInRequestCode = 0

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    Log.i("TAG", "Google sign in failed 1")
                } else {
                    /**
                     * Falta por a navegacao aqui caso o login seja OK
                     */
                    coroutineScope.launch {
                        val user = mainViewModel.signIn(
                            id = account.id!!,
                            email = account.email!!,
                            displayName = account.displayName!!,
                            photoUrl = account.photoUrl!!.toString()
                        )

                        //cadastrando usuário
                        user.let {
                            mainViewModel.createGoogleUserInLocalDatabase(it.value!!)
                        }

                    }
                }
            } catch (e: ApiException) {
                Log.i("TAG", "Google sign in failed 2")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            //recuperando usuarios apenas para testes, essa chamada deve ser implementada em outra tela
            Log.i("TAG", mainViewModel.getAllGoogleUsers().toString())
        }






    Column(
        modifier = Modifier
            .fillMaxSize(1.0f),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.2f),
            painter = painterResource(id = R.drawable.logotipo_st),
            contentDescription = stringResource(R.string.DER_Logo_desc)
        )

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

                        Spacer(modifier = Modifier.height(4.dp))

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


                        Spacer(modifier = Modifier.height(4.dp))

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
                                    mainViewModel.navHostController
                                        .navigate(Screens.MainScreen.name)
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
                            isLoading = isLoadingState,
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

@RequiresApi(Build.VERSION_CODES.R)
@Composable
@Preview(showBackground = true)
fun AuthScreenContentPreview() {

    val mainViewModel: MainViewModel = hiltViewModel()

    AuthScreenContent(
        mainViewModel = mainViewModel,
        wannaRegister = remember { mutableStateOf(false) }
    )
}
