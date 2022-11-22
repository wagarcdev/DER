package com.wagarcdev.der.presentation.screens.screen_create_report.steps

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.wagarcdev.der.presentation.ui.widgets.ClearTrailingButton
import com.wagarcdev.der.presentation.ui.widgets.DerOutlinedTextField

@Composable
fun FirstStep(
    modifier: Modifier = Modifier,
    text: String,
    textError: String?,
    changeText: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    BackHandler(onBack = onBack)

    Column(
        modifier = modifier.verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        DerOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { changeText(it) },
            labelString = "Hey hey",
            placeholderString = "Type something here",
            description = "First step sample field...",
            errorMessage = textError,
            trailingIcon = if (text.isEmpty()) null else {
                {
                    ClearTrailingButton(
                        onClick = { changeText("") }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        Spacer(modifier = Modifier.weight(weight = 1F))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext
        ) {
            Text(text = "Next")
        }
    }
}