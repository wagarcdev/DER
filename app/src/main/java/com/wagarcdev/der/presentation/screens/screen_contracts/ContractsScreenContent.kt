package com.wagarcdev.der.presentation.screens.screen_contracts

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.SignInGoogleViewModel
import com.wagarcdev.der.data.local.contracts
import com.wagarcdev.der.navigation.Screens
import com.wagarcdev.der.presentation.ui.components.ContractCard
import com.wagarcdev.der.presentation.ui.components.SearchBarRow
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContractsScreenContent(
    signInGoogleViewModel: SignInGoogleViewModel,
    mainViewModel: MainViewModel
) {
    val maxWidthFloat = 0.95f
    val coroutineScope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val context = LocalContext.current

    //Recuperando dados do usuario
    val googleUser = signInGoogleViewModel.user.value
    if (googleUser == null) {
        coroutineScope.launch {
            val userId = mainViewModel.getUserIdFromDatastore()
            val user = mainViewModel.getUserById(userId.toString())
            name.value = user.username.toString()
        }
    } else {
        name.value = googleUser.displayName.toString()
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
                text = "Contratos: ${name.value}",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
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
                            onclick = {
                                mainViewModel.navHostController
                                    .navigate(Screens.ReportsScreen.name)
                            }
                        )
                    }
                }
            }
        }

    }
}