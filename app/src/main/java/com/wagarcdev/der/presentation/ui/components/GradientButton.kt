package com.wagarcdev.der.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.common.util.CollectionUtils
import com.wagarcdev.der.presentation.ui.theme.DER_yellow
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_intense
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_light
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_light_extra


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
                    if (enabled) gradient else Brush.verticalGradient(listOf(Color.Gray, Color.LightGray)))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true
        ) {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


