package com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.steps

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.presentation.ui.components.ClearTrailingButton
import com.wagarcdev.der.presentation.ui.components.TempDerOutlinedTextField

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
    onNavigateBack: () -> Unit,
    onNext: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

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

    Column(
        modifier = modifier.verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.name,
            onValueChange = { changeName(it) },
            labelString = "Name",
            trailingIcon = if (report.name.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeName("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.regionCode,
            onValueChange = { changeRegionCode(it) },
            labelString = "Region code",
            trailingIcon = if (report.regionCode.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeRegionCode("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.highway,
            onValueChange = { changeHighway(it) },
            labelString = "Highway",
            trailingIcon = if (report.highway.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeHighway("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.county,
            onValueChange = { changeCounty(it) },
            labelString = "County",
            trailingIcon = if (report.county.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeCounty("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.contractor,
            onValueChange = { changeContractor(it) },
            labelString = "Contractor",
            trailingIcon = if (report.contractor.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeContractor("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.areaExtension,
            onValueChange = { changeAreaExtension(it) },
            labelString = "Area Extension",
            trailingIcon = if (report.areaExtension.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeAreaExtension("") }) }
            },
            keyboardOptions = defaultKeyboardOptions,
            keyboardActions = defaultKeyBoardActions
        )

        TempDerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = report.supervisor,
            onValueChange = { changeSupervisor(it) },
            labelString = "Supervisor",
            trailingIcon = if (report.supervisor.isEmpty()) null else {
                { ClearTrailingButton(onClick = { changeSupervisor("") }) }
            },
            keyboardOptions = defaultKeyboardOptions.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.weight(weight = 1F))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext,
            shape = RoundedCornerShape(size = 15.dp)
        ) {
            Text(text = "Next")
        }
    }
}