package com.wagarcdev.der.presentation.screens.screen_auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wagarcdev.der.R
import com.wagarcdev.der.presentation.navigation.graphs.AuthScreens
import com.wagarcdev.der.presentation.ui.components.SignInButton
import com.wagarcdev.der.presentation.ui.theme.DER_gray
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.presentation.ui.theme.RB_Black
import com.wagarcdev.der.presentation.ui.theme.RB_White
import kotlinx.coroutines.launch

@Composable
fun RecoverScreen(navHostController: NavHostController) {

    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirmation = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    val buttonFillWidthFloat = 0.7f

    val textFieldColors = DER_yellow

    Column(
        modifier = Modifier
            .imePadding()
            .defaultMinSize(300.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(buttonFillWidthFloat),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Forgot your password?",
                    color = RB_White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 18.dp)
                    .fillMaxWidth(buttonFillWidthFloat),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Please, enter you mail to generate a link to reset your password",
                    color = DER_gray,
                    fontSize = 10.sp,
                )

            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {



                    OutlinedTextField(
                        value = email.value,
                        onValueChange = {
                            coroutineScope.launch {
                                email.value = it
                            }
                        },
                        label = { Text(text = "Email", color = textFieldColors) },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = textFieldColors,
                            cursorColor = textFieldColors,
                            focusedLabelColor = textFieldColors,
                            unfocusedLabelColor = textFieldColors,
                            backgroundColor = RB_Black
                        )
                    )



                }
            }



            /** RECOVER PASSWORD BUTTON*/
            item {
                SignInButton(
                    buttonVerticalPaddingDp = 20.dp,
                    buttonFillMaxWidthFloat = buttonFillWidthFloat,
                    iconID = R.drawable.ic_log_in,
                    iconDescription = "Send reset link button",
                    text = "Get Reset Link",
                    textAlign = TextAlign.Center,
                    textFillMaxWidthFloat = 0.85f,
                    borderStroke = BorderStroke(0.dp, Color.Transparent),
                    leftGradientColor = RB_White,
                    centerLeftGradientColor = RB_White,
                    centerGradientColor = RB_White,
                    centerRightGradientColor = RB_White,
                    rightGradientColor = RB_White,
                    textFontColor = RB_White,
                    isLoading = false
                ) {
                    // TODO onClick
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 4.dp, bottom = 18.dp)
                        .fillMaxWidth(buttonFillWidthFloat),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp)
                            .clickable {
                                       navHostController
                                           .navigate(AuthScreens.Login.route)
                            },
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back to Log In",
                        tint = RB_White

                    )

                    Text(
                        modifier = Modifier
                            .clickable {
                                navHostController
                                    .navigate(AuthScreens.Login.route)
                            },
                        text = "Go back to  ",
                        color = DER_gray,
                        fontSize = 10.sp
                    )


                    Text(
                        modifier = Modifier
                            .clickable {
                                navHostController
                                    .navigate(AuthScreens.Login.route)
                            },
                        text = "Log In",
                        color = DER_gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
            }
        }
    }

}