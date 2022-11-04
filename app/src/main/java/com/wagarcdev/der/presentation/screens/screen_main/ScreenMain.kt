package com.wagarcdev.der.presentation.screens.screen_main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel
import com.wagarcdev.der.data.local.contracts
import com.wagarcdev.der.navigation.Screens
import com.wagarcdev.der.ui.widgets.ContractCard
import com.wagarcdev.der.ui.widgets.SearchBarRow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    signInGoogleViewModel: SignInGoogleViewModel
) {

    Scaffold(
        topBar = { },
        content = { MainScreenContent(signInGoogleViewModel, mainViewModel) },
        backgroundColor = Color.Gray
    )



}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    signInGoogleViewModel: SignInGoogleViewModel,
    mainViewModel: MainViewModel
    ) {
    val maxWidthFloat = 0.95f
    val coroutineScope = rememberCoroutineScope()
    val userName = remember { mutableStateOf("") }

    //já conseguimos pegar os dados do usuario logado com o google
    val currentUser = signInGoogleViewModel.googleUser.value?.displayName
    if (currentUser.equals(null)){
        //significa que o usuario nao fez login com o google
        //logar com room
        userName.value = "Room name"
    }else{
        coroutineScope.launch {
            userName.value = currentUser!!
        }
    }




    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(maxWidthFloat),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 6.dp),
                text = "Contratos de ${userName.value}",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White
            )
        }

        SearchBarRow(maxWidthFloat)

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                contracts.forEach { contract ->
                    item {
                        ContractCard(
                            maxWidthFloat,
                            contract,
                            onclick = { mainViewModel.navHostController
                                .navigate(Screens.ReportsScreen.name) }
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(
) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val signInGoogleViewModel: SignInGoogleViewModel = hiltViewModel()

    MainScreen(mainViewModel,signInGoogleViewModel)
}

