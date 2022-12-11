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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.presentation.ui.theme.DER_gray
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.utils.NoReportsDivider

@Composable
fun ReportsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToCreateReportScreen: () -> Unit
) {
    BackHandler(onBack = onNavigateBack)

    Scaffold(modifier = modifier) { innerPadding ->
        ReportScreenContent(
            innerPadding = innerPadding,
            onCreateReport = onNavigateToCreateReportScreen
        )
    }
}

@Composable
fun ReportScreenContent(
    innerPadding: PaddingValues,
    onCreateReport: () -> Unit
) {
    val listOfReports = listOf<Report>()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text(
                text = "Contrato Nº xxxx",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DER_gray
            )
        }

        item {
            Text(
                text = "Construtora XXX",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DER_gray
            )
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
            CreateReportCard(onCreateReport = onCreateReport)
        }

        if (listOfReports.isEmpty()) {
            item { NoReportsDivider() }
        } else {
            /* TODO */
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