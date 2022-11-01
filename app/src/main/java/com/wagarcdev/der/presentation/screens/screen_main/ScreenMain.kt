package com.wagarcdev.der.presentation.screens.screen_main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.wagarcdev.der.data.local.contracts
import com.wagarcdev.der.ui.widgets.ContractCard
import com.wagarcdev.der.ui.widgets.SearchBarRow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {

    Scaffold(
        topBar = {  },
        content = { MainScreenContent() },
        backgroundColor = Color.Gray
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent() {

    val maxWidthFloat = 0.95f

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
                text = "Contratos",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White
            )
        }

        SearchBarRow(maxWidthFloat)

        CompositionLocalProvider(LocalOverScrollConfiguration provides null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                contracts.forEach { contract ->
                    item {
                        ContractCard(maxWidthFloat, contract)
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

    MainScreen(mainViewModel)
}

