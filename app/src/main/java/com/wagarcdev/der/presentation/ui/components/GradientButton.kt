package com.wagarcdev.der.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GradientButton(
    modifier: Modifier,
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        enabled = enabled
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (enabled) {
                        gradient
                    } else {
                        Brush.verticalGradient(
                            listOf(
                                Color.LightGray,
                                Color.Gray)
                        )
                    }
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true
        ) {
            Text(
                text = text,
                color = if (enabled) textColor else Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


