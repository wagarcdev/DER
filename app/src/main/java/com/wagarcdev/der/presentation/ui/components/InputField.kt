package com.wagarcdev.der.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wagarcdev.der.R
import com.wagarcdev.der.presentation.ui.theme.DER_yellow

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    isError: MutableState<Boolean> = mutableStateOf(false),
    errorMessage: String = "teste",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
) {


    Column {
        OutlinedTextField(
            value = valueState.value,
            onValueChange = { valueState.value = it },
            label = { Text(text = labelId, color = Color.Gray) },
            shape = RoundedCornerShape(15.dp),
            leadingIcon = leadingIcon,
            isError = isError.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = DER_yellow,
                unfocusedLabelColor = Color.Black,
                backgroundColor = Color.White
            ),
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            modifier = modifier
                .padding(start = 10.dp, end = 10.dp)
                .focusRequester(focusRequester),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction,
            trailingIcon = trailingIcon)

        if (isError.value && valueState.value.isEmpty()) {
            Text(
                text = errorMessage, color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 30.dp)
            )
        }

    }
}

@Composable
fun InputFieldPassword(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    isError: MutableState<Boolean> = mutableStateOf(false),
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val visualTransformation =
        if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()

    val icon = if (passwordVisibility) painterResource(id = R.drawable.ic_visibility)
    else painterResource(id = R.drawable.ic_visibility_off)

    Column {
        OutlinedTextField(
            value = valueState.value,
            onValueChange = { valueState.value = it },
            label = { Text(text = labelId, color = Color.Gray) },
            shape = RoundedCornerShape(15.dp),
            leadingIcon = leadingIcon,
            isError = isError.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = DER_yellow,
                unfocusedLabelColor = Color.Black,
                backgroundColor = Color.White
            ),
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            modifier = modifier
                .padding(start = 10.dp, end = 10.dp)
                .focusRequester(focusRequester),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction,
            visualTransformation = visualTransformation,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(painter = icon, contentDescription = "")
                }
            }
        )
    }
}