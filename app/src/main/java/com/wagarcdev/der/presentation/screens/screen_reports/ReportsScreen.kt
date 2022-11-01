package com.wagarcdev.der.presentation.screens.screen_reports

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.utils.NoReportsDivider

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportsScreen(
    mainViewModel: MainViewModel
) {

    val listOfReports = listOf<Report>()

    Scaffold() {

        if (listOfReports.isEmpty()) {
            NoReportsDivider()
        } else {
            Text(text = "TEST TEXT")
        }
    }






}


@Composable
@Preview(showBackground = true)
fun ReportScreenPreview() {

    val mainViewModel: MainViewModel = hiltViewModel()

    ReportsScreen(mainViewModel)
}

