package com.wagarcdev.der.presentation.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_intense
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_light
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_light_extra

@Composable
fun SignUpButton(
    onClick: () -> Unit,
    buttonText: String,
    enable: Boolean = true
) {

    GradientButton(
        modifier = Modifier
            .height(48.dp)
            .width(140.dp),
        text = buttonText,
        textColor = Color.Black,
        gradient = Brush.verticalGradient(
            listOf(
                DER_yellow_light_extra,
                DER_yellow_light,
                DER_yellow,
                DER_yellow,
                DER_yellow_intense
            )
        ),
        onClick = { onClick() },
        enabled = enable
    )
}