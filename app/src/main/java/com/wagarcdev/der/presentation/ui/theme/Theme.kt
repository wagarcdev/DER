package com.wagarcdev.der.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = DER_yellow,
    onPrimary = Color.Black,
    primaryVariant = DER_yellow_deep,
    secondary = DER_yellow,
    onSecondary = Color.Black,
    secondaryVariant = DER_yellow_deep,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun DERTheme(
    content: @Composable () -> Unit
) = MaterialTheme(
    colors = LightColorPalette,
    typography = Typography(),
    shapes = Shapes(),
    content = content
)