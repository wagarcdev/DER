package com.wagarcdev.der.presentation.screens.screen_reports

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddRoad
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.CreateOrEditReportViewModel

/**
 * Compose the Reports Screen.
 *
 * @param modifier the [Modifier] to be applied on main container of this screen.
 * @param onNavigateBack callback to navigate back from this screen.
 * @param onNavigateToCreateReportScreen callback to navigate to create report screen.
 * @param onNavigateToEditReportScreen callback to navigate to edit report screen.
 * @param viewModel the [ReportsViewModel]. Default is provided by [hiltViewModel].
 */
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalAnimationApi::class)
@Composable
fun ReportsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToCreateReportScreen: (String) -> Unit,
    onNavigateToEditReportScreen: (String) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val screenState by viewModel.reportsState.collectAsStateWithLifecycle()

    BackHandler(onBack = onNavigateBack)

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (screenState is ReportsState.Success) {
                val contractId = (screenState as ReportsState.Success).contract.number
                FloatingActionButton(
                    onClick = { onNavigateToCreateReportScreen(contractId) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddRoad,
                        contentDescription = "Create report"
                    )
                }
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            targetState = screenState
        ) { state ->
            when (state) {
                ReportsState.Loading -> LoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp)
                )
                is ReportsState.Error -> ErrorState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp),
                    state = state,
                    onTryAgain = viewModel::reloadContractData
                )
                is ReportsState.Success -> SuccessState(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onReportClick = { reportId ->
                        onNavigateToEditReportScreen(reportId)
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    CircularProgressIndicator()
}

@Composable
private fun ErrorState(
    modifier: Modifier,
    state: ReportsState.Error,
    onTryAgain: () -> Unit
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = state.message,
        textAlign = TextAlign.Center
    )

    if (state.hasReload) {
        Spacer(modifier = Modifier.height(height = 16.dp))

        Button(
            onClick = onTryAgain,
            shape = RoundedCornerShape(size = 15.dp)
        ) {
            Text(text = "Try again")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SuccessState(
    modifier: Modifier,
    state: ReportsState.Success,
    onReportClick: (String) -> Unit
) = Column(modifier = modifier) {
    Spacer(modifier = Modifier.height(height = 4.dp))

    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .align(alignment = Alignment.CenterHorizontally),
        text = "Contract ${state.contract.number}",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6
    )

    Spacer(modifier = Modifier.height(height = 2.dp))

    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .align(alignment = Alignment.CenterHorizontally),
        text = "Company ${state.contract.company}",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6
    )

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    )

    AnimatedVisibility(visible = state.reports.isEmpty()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            text = "This contract don't have any reports yet",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        items(items = state.reports) { report ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(size = 15.dp),
                onClick = { onReportClick(report.id) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(weight = 1F),
                        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
                    ) {
                        Text(text = "Name: ${report.name}")

                        Text(text = "Day period: ${report.dayPeriod.name}")

                        Text(text = "Climate: ${report.climate.name}")

                    }

                    Icon(
                        imageVector = Icons.Rounded.NavigateNext,
                        contentDescription = null
                    )

                }
            }
        }

        passByFabSize()
    }
}

private fun LazyListScope.passByFabSize() = item {
    Spacer(modifier = Modifier.height(height = 56.dp))
}
