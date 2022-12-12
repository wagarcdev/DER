package com.wagarcdev.der.presentation.screens.screen_contracts

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wagarcdev.der.data.datasource.local.contracts
import com.wagarcdev.der.presentation.ui.components.ContractCard
import com.wagarcdev.der.presentation.ui.components.SearchBarRow

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun ContractsScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToReportsScreen: (String) -> Unit,
    viewModel: ContractsViewModel = hiltViewModel()
) {
    val screenState by viewModel.contractsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { channel ->
            when (channel) {
                ContractsChannel.RedirectToLogin -> onNavigateToLoginScreen()
            }
        }
    }

    val maxWidthFloat = 0.95f

    BackHandler(onBack = onNavigateBack)

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.Gray
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(maxWidthFloat),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "Contratos: ${screenState.userName}",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )

                IconButton(onClick = viewModel::logout) {
                    Icon(
                        imageVector = Icons.Rounded.Logout,
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            }

            SearchBarRow(maxWidthFloat)

            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {
                    // todo migrate contracts to view model

                    contracts.forEach { contract ->
                        item {
                            ContractCard(
                                maxWidthFloat,
                                contract,
                                onclick = { onNavigateToReportsScreen(contract.number) }
                            )
                        }
                    }
                }
            }
        }
    }
}