package com.wagarcdev.der.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wagarcdev.der.R
import com.wagarcdev.der.data.datasource.local.contracts
import com.wagarcdev.der.domain.model.Contract
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_deep_plus
import com.wagarcdev.der.presentation.ui.theme.DER_yellow_intense

@Composable
fun ContractCard(
    contract: Contract,
    maxWidthFloat: Float = 0.9f,
    cardBorder : BorderStroke = BorderStroke(2.dp, Color.DarkGray),
    onclick: () -> Unit = { },
) {

    val defaultPadding = 2.dp
    val startEndRowCardColor = DER_yellow_deep_plus


    Card(
        modifier = Modifier
            .fillMaxWidth(maxWidthFloat)
            .padding(defaultPadding)
            .clickable { onclick.invoke() },
        border = cardBorder,
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp,
        backgroundColor = Color.White
    ) {

        Column(
            modifier = Modifier
                .padding(defaultPadding * 2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            val headerIconsDp = 32.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = defaultPadding * 2),
                    text = contract.company,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .padding(defaultPadding * 2)
                    .height(headerIconsDp + defaultPadding)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                DoubleCardWithText(
                    cardBackgroundColor = DER_yellow_intense,
                    cardBorder = BorderStroke(4.dp, DER_yellow_intense),
                    contentsPadding = defaultPadding,
                    iconContent = {
                        Image(
                            modifier = Modifier
                                .padding(vertical = defaultPadding)
                                .size(headerIconsDp),
                            painter = painterResource(id = R.drawable.der_logo),
                            contentDescription = "Ícone rodovia",
                        )
                    },
                    text = contract.road,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )

                DoubleCardWithText(
                    cardBackgroundColor = Color.Gray,
                    cardBorder = BorderStroke(4.dp, Color.Gray),
                    contentsPadding = defaultPadding,
                    iconContent = {
                        Icon(
                            modifier = Modifier
                                .padding(vertical = defaultPadding, horizontal = 0.dp)
                                .size(headerIconsDp),
                            painter = painterResource(id = R.drawable.ic_contract),
                            contentDescription = "ícone contrato",
                            tint = Color.White
                        )
                    },
                    text = contract.number,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                )
            }

            /** MIDDLE ROW */
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding * 2),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Row(
                    modifier = Modifier
                        .padding(defaultPadding * 2)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {




                    /** IMAGE COLUMN*/
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(defaultPadding * 2),
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Image(
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 100.dp, minWidth = 100.dp),
                                painter = painterResource(id = contract.companyLogo),
                                contentDescription = "company logo",
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    /** INFO COLUMN */
                    Column(
                        modifier = Modifier
                            .padding(start = defaultPadding)
                            .fillMaxWidth(0.55f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = defaultPadding),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            DoubleCardWithText(
                                cardBackgroundColor = Color.DarkGray,
                                cardBorder = BorderStroke(1.dp, Color.DarkGray),
                                contentsPadding = defaultPadding,
                                iconContent = {
                                    Icon(
                                        modifier = Modifier
                                            .padding(defaultPadding)
                                            .size(22.dp),
                                        painter = painterResource(id = R.drawable.ic_location_city),
                                        contentDescription = "Ícone cidade",
                                        tint = Color.White
                                    )
                                },
                                text = contract.city,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = defaultPadding),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(defaultPadding)
                        ) {

                            val block = contract.block
                            val blockFormat = String.format("%02d", block)
                            DoubleCardWithText(
                                cardBackgroundColor = Color.Gray,
                                cardBorder = BorderStroke(1.dp, Color.Gray),
                                contentsPadding = defaultPadding,
                                iconContent = {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = defaultPadding),
                                        text = "Bloco ",
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                },
                                text = blockFormat ,
                                fontWeight = FontWeight.Bold,
                                fontColor = Color.Gray,
                                fontSize = 14.sp
                            )


                            val plot = contract.plot
                            val plotFormat = String.format("%02d", plot)
                            DoubleCardWithText(
                                cardBackgroundColor = Color.Gray,
                                cardBorder = BorderStroke(1.dp, Color.Gray),
                                contentsPadding = defaultPadding,
                                iconContent = {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = defaultPadding),
                                        text = "Lote ",
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                },
                                text = plotFormat ,
                                fontWeight = FontWeight.Bold,
                                fontColor = Color.Gray,
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = defaultPadding),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            val constructionExt = contract.extensionInMeters
                            val constructionExtFormat = String
                                .format("%,d", constructionExt).replace(",", ".")
                            DoubleCardWithText(
                                cardBackgroundColor = Color.Gray,
                                cardBorder = BorderStroke(1.dp, Color.Gray),
                                contentsPadding = defaultPadding,
                                iconContent = {
                                    Icon(
                                        modifier = Modifier
                                            .padding(horizontal = defaultPadding)
                                            .size(22.dp),
                                        painter = painterResource(id = R.drawable.ic_construction),
                                        contentDescription = "Ícone obra",
                                        tint = Color.White
                                    )
                                },
                                text = "$constructionExtFormat m",
                                fontWeight = FontWeight.Bold,
                                fontColor = Color.Gray,
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = defaultPadding),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            DoubleCard(
                                cardBackgroundColor = Color.White,
                                cardBorder = BorderStroke(1.dp, startEndRowCardColor),
                                contentsPadding = 0.dp,
                                iconContent = {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = defaultPadding),
                                        text = "Início",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.DarkGray
                                    )
                                },
                                internalContent = {
                                    val startKm = contract.startKm
                                    val startKmFormat = String.format("%02d", startKm)

                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = defaultPadding),
                                        text = "KM $startKmFormat",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )

                                    if(contract.startAdjustInMeters > 0) {
                                        Text(
                                            text = " +${contract.startAdjustInMeters}m",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFF7E99E)
                                        )
                                    }
                                },
                                internalCardBackgroundColor = startEndRowCardColor
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = defaultPadding),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            DoubleCard(
                                cardBackgroundColor = Color.White,
                                cardBorder = BorderStroke(1.dp, startEndRowCardColor),
                                contentsPadding = 0.dp,
                                iconContent = {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = defaultPadding),
                                        text = "Final",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.DarkGray
                                    )
                                },
                                internalContent = {
                                    val endKm = contract.endKm
                                    val endKmFormat = String.format("%02d", endKm)


                                    Text(
                                        text = "KM $endKmFormat",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )

                                    if (contract.endAdjustInMeters > 0) {
                                        Text(
                                            text = " +${contract.endAdjustInMeters}m",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFF7E99E)
                                        )
                                    }
                                },
                                internalCardBackgroundColor = startEndRowCardColor
                            )
                        }
                    }
                    /** INFO COLUMN */
                }
            }
        }
    }
}

@Composable
fun DoubleCard(
    cardBackgroundColor: Color,
    cardBorder: BorderStroke,
    internalCardBackgroundColor: Color = Color.White,
    internalCardBorder: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    contentsPadding: Dp,
    iconContent: @Composable () -> Unit,
    internalContent: @Composable () -> Unit,
) {
    Card(
        border = cardBorder,
        backgroundColor = cardBackgroundColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = contentsPadding),
            ) { iconContent() }





            Card(
                backgroundColor = internalCardBackgroundColor,
                border = internalCardBorder
            ) {

                Box(
                    modifier = Modifier
                        .padding(horizontal = contentsPadding),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(contentsPadding),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        internalContent()
                    }
                }
            }
        }
    }

}

@Composable
private fun DoubleCardWithText(
    cardBackgroundColor: Color,
    cardBorder: BorderStroke,
    internalCardBackgroundColor: Color = Color.White,
    internalCardBorder: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    contentsPadding: Dp,
    iconContent: @Composable () -> Unit,
    text: String,
    fontColor: Color = Color.DarkGray,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 15.sp,

) {

    DoubleCard(
        cardBackgroundColor = cardBackgroundColor,
        cardBorder = cardBorder,
        contentsPadding = contentsPadding,
        iconContent = iconContent,
        internalContent = {
            Text(
                modifier = Modifier
                    .padding(horizontal = contentsPadding),
                text = text,
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = fontColor
            )
        },
        internalCardBackgroundColor = internalCardBackgroundColor,
        internalCardBorder = internalCardBorder
    )


}

@Preview
@Composable
fun ContractCardPreview() {

    Box(
        Modifier.size(500.dp)
    ) {
        ContractCard(
            contract = contracts[1]
        ) {  }
    }
}