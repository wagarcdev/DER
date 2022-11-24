package com.wagarcdev.der.presentation.screens.screen_auth

//@Composable
//fun OldRegisterContent(
//    mainViewModel: MainViewModel,
//    wannaRegister: MutableState<Boolean>,
//) {
//
//    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current
//    val username = remember { mutableStateOf("") }
//    val fullName = remember { mutableStateOf("") }
//    val email = remember { mutableStateOf("") }
//    val password = remember { mutableStateOf("") }
//    val passwordConfirm = remember { mutableStateOf("") }
//
//
//    fun checkIfPasswordAreEquals(): Boolean {
//        if (password.value == passwordConfirm.value) {
//            return true
//        }
//        return false
//    }
//
//    fun createSimpleUser() {
//        if (username.value.isNotEmpty() && fullName.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && passwordConfirm.value.isNotEmpty()) {
//            if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
//                val isEqualsPassword = checkIfPasswordAreEquals()
//                if (isEqualsPassword) {
//                    coroutineScope.launch {
//                        val simpleUser =
//                            User(
//                                System.currentTimeMillis().toString(),
//                                email.value,
//                                username.value,
//                                fullName.value,
//                                fullName.value,
//                                "",
//                                password.value,
//                                true
//                            )
//                        mainViewModel.createNewSimpleUser(simpleUser).also {
//                            mainViewModel.navHostController
//                                .navigate(Screens.AuthScreen.name)
//                        }
//
//                    }
//                } else {
//                    Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_LONG).show()
//                }
//            } else {
//                Toast.makeText(context, "Email não está de acordo", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            Toast.makeText(context, "Preencha todos os campos corretamente", Toast.LENGTH_LONG)
//                .show()
//        }
//
//
//    }
//
//    LazyColumn(
//        modifier = Modifier
//            .systemBarsPadding()
//            .navigationBarsPadding(),
//        verticalArrangement = Arrangement.SpaceEvenly,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        item {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(0.7f)
//                    .wrapContentHeight(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceEvenly
//
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .padding(top = 2.dp)
//                            .fillMaxWidth(1f),
//                        horizontalArrangement = Arrangement.Start
//                    ) {
//                        Text(
//                            text = "Novo cadastro",
//                            color = Color.Black,
//                            fontSize = 42.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .padding(top = 2.dp, bottom = 2.dp)
//                            .fillMaxWidth(1f),
//                        horizontalArrangement = Arrangement.Start
//                    ) {
//                        Text(
//                            modifier = Modifier.alpha(1f),
//                            text = stringResource(com.wagarcdev.der.R.string.registro_frase),
//                            color = Color.Black,
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
//                }
//            }
//        }
//
//        item {
//            Spacer(modifier = Modifier.height(32.dp))
//
//        }
//        item {
//            Box(contentAlignment = Alignment.Center) {
//
//                BackgroundImageRow(imageResInt = R.drawable.backgroung_gray_img)
//
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(1f),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth(1.0f),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            val isFullnameError = remember {
//                                mutableStateOf(false)
//                            }
//                            val isEmailError = remember {
//                                mutableStateOf(false)
//                            }
//
//                            val isUsernameError = remember {
//                                mutableStateOf(false)
//                            }
//                            val isPasswordError = remember {
//                                mutableStateOf(false)
//                            }
//
//                            val isPasswordConfirmError = remember {
//                                mutableStateOf(false)
//                            }
//
//
//                            val localFocusManager = LocalFocusManager.current
//                            val focusRequester = FocusRequester()
//
//
//                            InputField(
//                                modifier = Modifier.onFocusChanged {
//                                    if (it.isFocused) {
//                                        isFullnameError.value = false
//                                    }
//                                },
//                                valueState = fullName,
//                                labelId = "Nome completo",
//                                enabled = true,
//                                isSingleLine = true,
//                                isError = isFullnameError,
//                                errorMessage = "Preencha seu nome completo",
//                                focusRequester = focusRequester
//                            )
//
//                            InputField(
//                                valueState = email,
//                                labelId = "E-mail",
//                                enabled = true,
//                                isSingleLine = true,
//                                isError = isEmailError,
//                                errorMessage = "Preencha o email",
//                                focusRequester = focusRequester
//                            )
//
//                            InputField(
//                                valueState = username,
//                                labelId = "Usuário",
//                                enabled = true,
//                                isSingleLine = true,
//                                isError = isUsernameError,
//                                errorMessage = "Preencha o nome de usuario",
//                                focusRequester = focusRequester
//                            )
//
//                            InputField(
//                                valueState = password,
//                                labelId = "Senha",
//                                enabled = true,
//                                isSingleLine = true,
//                                isPassword = true,
//                                isError = isPasswordError,
//                                errorMessage = "Preencha a senha",
//                                focusRequester = focusRequester
//                            )
//
//                            InputField(
//                                valueState = passwordConfirm,
//                                labelId = "Repita a senha",
//                                enabled = true,
//                                isSingleLine = true,
//                                isPassword = true,
//                                isError = isPasswordConfirmError,
//                                errorMessage = "Preencha a confirmação da senha",
//                                focusRequester = focusRequester
//                            )
//
//                        }
//                    }
//                }
//
//            }
//        }
//
//        item {
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//
//        item {
//            Column(
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//                val validState = remember(
//                    fullName.value,
//                    username.value,
//                    email.value,
//                    password.value,
//                    passwordConfirm.value
//                ) {
//                    fullName.value.trim().isNotEmpty() && username.value.trim()
//                        .isNotEmpty()
//                            && email.value.trim().isNotEmpty() && password.value.trim()
//                        .isNotEmpty() && passwordConfirm.value.trim()
//                        .isNotEmpty()
//                }
//
//                SignUpButton(
//                    onClick = {
//                        createSimpleUser()
//                        mainViewModel.navHostController.navigate(Screens.AuthScreen.name)
//                    },
//                    enable = validState
//                )
//
//                Row(
//                    modifier = Modifier
//                        .padding(vertical = 4.dp)
//                        .wrapContentWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End
//
//                ) {
//                    Text(
//                        text = "Já possui cadastro? ",
//                        color = Color.Black,
//                        fontSize = 13.sp
//                    )
//
//                    Text(
//                        modifier = Modifier
//                            .clickable {
//                                coroutineScope.launch {
//                                    wannaRegister.value = false
//                                }
//                            },
//                        text = "Efetue o seu Login!",
//                        color = DER_yellow,
//                        textDecoration = TextDecoration.Underline,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 13.sp
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(32.dp))
//
//                Image(
//                    modifier = Modifier.fillMaxWidth(0.5f),
//                    painter = painterResource(id = com.wagarcdev.der.R.drawable.logotipo_st),
//                    contentDescription = stringResource(com.wagarcdev.der.R.string.Logo_ST6),
//                    contentScale = ContentScale.Fit
//
//                )
//            }
//        }
//
//
//    }
//}

//@Composable
//@Preview(showBackground = true)
//fun RegisterScreenContentPreview() {
//
//    val mainViewModel: MainViewModel = hiltViewModel()
//
//    RegisterContent(
//        mainViewModel = mainViewModel,
//        wannaRegister = remember { mutableStateOf(false) }
//    )
//
//}
//
//@Composable
//fun SignUpButton(onClick: () -> Unit, enable: Boolean) {
//    GradientButton(
//        modifier = Modifier
//            .height(48.dp)
//            .width(140.dp),
//        text = "Cadastrar",
//        textColor = Color.Black,
//        gradient = Brush
//            .verticalGradient(
//                listOf(
//                    DER_yellow_light_extra,
//                    DER_yellow_light,
//                    DER_yellow,
//                    DER_yellow,
//                    DER_yellow_intense
//                )
//            ),
//        onClick = { onClick() },
//        enabled = enable
//    )
//}