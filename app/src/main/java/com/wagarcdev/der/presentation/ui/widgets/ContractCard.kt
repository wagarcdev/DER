package com.wagarcdev.der.presentation.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.der.MainViewModel
import com.wagarcdev.der.data.local.contracts
import com.wagarcdev.der.domain.model.Contract
import com.wagarcdev.der.navigation.Screens
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_deep

@Composable
fun ContractCard(
    maxWidthFloat: Float,
    contract: Contract,
    onclick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(maxWidthFloat)
            .padding(4.dp)
            .clickable { onclick.invoke() },
        border = BorderStroke(2.dp, Color.DarkGray),
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        painter = painterResource(id = contract.companyLogo),
                        contentDescription = "company logo",
                        contentScale = ContentScale.Fit
                    )
                }


                Card(
                    modifier = Modifier
                        .padding(8.dp),
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

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = contract.company,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rodovia ${contract.road}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = DER_yellow_deep

                    )
                    Text(
                        text = contract.sections,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        text = contract.site,
                        fontSize = 14.sp,
                        color = Color.DarkGray

                    )

                    Row() {

                        Text(text = "Ext: ")
                        Text(
                            text = contract.extension,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Color.Red

                        )
                    }
                    Text(
                        text = contract.city,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }

            }
        }





    }
}

@Preview(showBackground = true)
@Composable
fun ContractCardPreview() {

    val mainViewModel: MainViewModel = hiltViewModel()

    ContractCard(0.95f, contracts[0], onclick = {
        mainViewModel.navHostController
        .navigate(Screens.ReportsScreen.name)
    })
}