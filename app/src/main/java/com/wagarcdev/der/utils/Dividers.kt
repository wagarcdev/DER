package com.wagarcdev.der.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Date


@Composable
fun NoReportsDivider() {

    val text = "Não há relatórios para esse contrato"

    SectionsDividerText(text)

}

@Composable
fun DateDivider(date: Date) {


    val text = date.time.toString()

    SectionsDividerText(text)

}



@Composable
fun SectionsDividerText(
    text: String
) {
    Row(
        modifier = Modifier
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
                color = Color.Black
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
            Text(text)
        }

        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth(1f)
        ) {
            Divider(
                modifier = Modifier
                    .height(2.dp),
                color = Color.Black
            )
        }


    }
}


@Composable
@Preview(showBackground = true)
fun NoReportDividerPreview() {
    NoReportsDivider()
}