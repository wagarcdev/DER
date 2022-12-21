package com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.steps

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.presentation.ui.components.ClearTrailingButton
import com.wagarcdev.der.presentation.ui.components.DerFilterChip
import com.wagarcdev.der.presentation.ui.components.TempDerOutlinedTextField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FirstStep(
    modifier: Modifier = Modifier,
    report: Report,
    changeName: (String) -> Unit,
    changeRegionCode: (String) -> Unit,
    changeHighway: (String) -> Unit,
    changeCounty: (String) -> Unit,
    changeContractor: (String) -> Unit,
    changeAreaExtension: (String) -> Unit,
    changeSupervisor: (String) -> Unit,
    changeClimate: (Climate) -> Unit,
    changeDayPeriod: (DayPeriod) -> Unit,
    onNavigateBack: () -> Unit,
    onNextStep: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val defaultItemModifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, top = 16.dp, end = 16.dp)

    val defaultKeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    )

    val defaultKeyBoardActions = KeyboardActions(
        onNext = {
            focusManager.moveFocus(focusDirection = FocusDirection.Down)
        }
    )

    BackHandler(onBack = onNavigateBack)



    Column(modifier = modifier.verticalScroll(state = scrollState)) {
        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.name,
            onValueChange = { changeName(it) },
            labelString = "Name",
            description = "A name to identify this report",
            trailingIcon = if (report.name.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeName("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.regionCode,
            onValueChange = { changeRegionCode(it) },
            labelString = "Region code",
            description = "Region code",
            trailingIcon = if (report.regionCode.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeRegionCode("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.highway,
            onValueChange = { changeHighway(it) },
            labelString = "Highway",
            description = "Highway name",
            trailingIcon = if (report.highway.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeHighway("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.county,
            onValueChange = { changeCounty(it) },
            labelString = "County",
            description = "County name",
            trailingIcon = if (report.county.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeCounty("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.contractor,
            onValueChange = { changeContractor(it) },
            labelString = "Contractor",
            description = "Contractor",
            trailingIcon = if (report.contractor.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeContractor("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.areaExtension,
            onValueChange = { changeAreaExtension(it) },
            labelString = "Area extension",
            description = "Area extension",
            trailingIcon = if (report.areaExtension.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeAreaExtension("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = defaultItemModifier,
            value = report.supervisor,
            onValueChange = { changeSupervisor(it) },
            labelString = "Supervisor",
            description = "Supervisor",
            trailingIcon = if (report.supervisor.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeSupervisor("") }) }
            },
            keyboardOptions = defaultKeyboardOptions.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Text(
            modifier = defaultItemModifier,
            text = "Climate:"
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 0.dp
        ) {
            Climate.values().forEach { climate ->
                DerFilterChip(
                    selected = report.climate == climate,
                    text = climate.name,
                    onClick = { changeClimate(climate) }
                )
            }
        }

        Text(
            modifier = defaultItemModifier,
            text = "Day Period:"
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 0.dp
        ) {
            DayPeriod.values().forEach { dayPeriod ->
                DerFilterChip(
                    selected = report.dayPeriod == dayPeriod,
                    text = dayPeriod.name,
                    onClick = { changeDayPeriod(dayPeriod) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(weight = 1F))

        Button(
            modifier = defaultItemModifier.padding(bottom = 16.dp),
            onClick = onNextStep,
            shape = RoundedCornerShape(size = 15.dp)
        ) {
            Text(text = "Next")
        }
    }
}