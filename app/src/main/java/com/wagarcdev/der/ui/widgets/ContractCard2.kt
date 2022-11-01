package com.wagarcdev.der.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.wagarcdev.der.data.local.contracts
import com.wagarcdev.der.domain.model.Contract
import com.wagarcdev.der.ui.theme.DER_gray
import com.wagarcdev.der.ui.theme.DER_yellow_deep
import com.wagarcdev.der.ui.theme.DER_yellow_intense
import com.wagarcdev.der.ui.theme.RB_White_75

@Composable
fun ContractCard2(
    maxWidthFloat: Float,
    contract: Contract
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(maxWidthFloat)
            .padding(4.dp)
            .clickable { },
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, Color.DarkGray),
        backgroundColor = DER_gray
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .height(60.dp),
                        model = contract.companyLogo,
                        contentDescription = "company logo",
                        contentScale = ContentScale.FillHeight
                    )
                }

                Card(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Contrato nº: ")
                        Text(
                            text = contract.number,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                text = contract.company,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row() {
                Text(
                    text = " ${contract.site}",
                    fontSize = 16.sp,
                    color = Color.DarkGray

                )
                Text(text = " / Ext: ")
                Text(
                    text = contract.extension,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Red

                )
            }


            Row() {
                Text(
                    text = contract.road,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = DER_yellow_deep

                )


                Text(
                    text = " - ${contract.city}",
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContractCard2Preview() {
    ContractCard2(0.95f, contracts[0])
}