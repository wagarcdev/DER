package com.wagarcdev.der.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wagarcdev.der.presentation.ui.theme.DER_gray
import java.util.*


@Composable
fun NoReportsDivider() {

    val text = "Não há relatórios salvos desse contrato"

    SectionsDividerText(text)

}

@Composable
fun DateDivider(date: Date) {


    val text = date.time.toString()

    SectionsDividerText(
        text = text,
        barsColor = DER_gray,
        textColor = DER_gray
    )

}



@Composable
fun SectionsDividerText(
    text: String,
    barsColor: Color = Color.Black,
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth(1f)
        ) {
            Divider(
                modifier = Modifier
                    .height(2.dp),
                color = barsColor
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(1f)
                .padding(horizontal = 2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = textColor

            )
        }

        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth(1f)
        ) {
            Divider(
                modifier = Modifier
                    .height(2.dp),
                color = barsColor
            )
        }


    }
}


@Composable
@Preview(showBackground = true)
fun NoReportDividerPreview() {
    NoReportsDivider()
}