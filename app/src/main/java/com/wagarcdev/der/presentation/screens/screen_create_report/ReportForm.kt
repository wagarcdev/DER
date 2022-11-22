package com.wagarcdev.der.presentation.screens.screen_create_report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.wagarcdev.der.presentation.ui.widgets.DerOutlinedTextField
import com.wagarcdev.der.presentation.ui.widgets.ClearTrailingButton

@Composable
fun ReportForm(
    modifier: Modifier = Modifier,
    text: String,
    textError: String?,
    changeText: (String) -> Unit
) = Column(modifier = modifier) {
    val focusManager = LocalFocusManager.current

    DerOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { changeText(it) },
        labelString = "Hey hey",
        placeholderString = "Type something here",
        description = "This is a example...",
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
}