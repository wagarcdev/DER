package com.wagarcdev.der.presentation.screens.screen_reports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddRoad
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wagarcdev.der.presentation.ui.theme.DER_gray
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.utils.NoReportsDivider

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ReportsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToCreateReportScreen: () -> Unit,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val screenState by viewModel.reportsState.collectAsStateWithLifecycle()

    BackHandler(onBack = onNavigateBack)

    Scaffold(modifier = modifier) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            screenState.contract?.let { contract ->
                item {
                    Text(
                        text = "Contrato Nº ${contract.number}",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DER_gray
                    )
                }

                item {
                    Text(
                        text = "Construtora ${contract.company}",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DER_gray
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 8.dp),
                        text = "Relatórios :",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                CreateReportCard(onCreateReport = onNavigateToCreateReportScreen)
            }

            if (screenState.reports.isEmpty()) {
                item { NoReportsDivider() }
            } else {
                /* TODO */
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateReportCard(
    onCreateReport: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(0.7f),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(3.dp, DER_yellow),
        onClick = onCreateReport
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp),
                imageVector = Icons.Rounded.AddRoad,
                contentDescription = "",
                tint = DER_gray
            )

            Text(
                text = "Novo Relatório",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}