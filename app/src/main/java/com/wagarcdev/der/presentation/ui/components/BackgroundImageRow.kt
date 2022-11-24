package com.wagarcdev.der.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun BackgroundImageRow(
    modifier: Modifier = Modifier,
    imageResInt: Int
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(0.25f)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = imageResInt),
            contentDescription = "background",
            contentScale = ContentScale.FillWidth
        )
    }

}