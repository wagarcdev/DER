package com.wagarcdev.der.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wagarcdev.der.presentation.ui.theme.DER_yellow

@Composable
fun SearchBarRow(widthFloat: Float) {


    val layoutHeight = 60.dp

    Row(
        modifier = Modifier
            .fillMaxWidth(widthFloat)
            .padding(start = 8.dp, end = 4.dp, bottom = 8.dp)
            .height(layoutHeight),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val sizeAdjustment = 0.dp

        Column(
            modifier = Modifier
                .height(layoutHeight),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val search = remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .height(layoutHeight - (sizeAdjustment))
                    .fillMaxWidth(0.8f),
                value = search.value,
                onValueChange = { search.value = it },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { search.value = "" },
                        painter = painterResource(com.wagarcdev.der.R.drawable.ic_cancel),
                        contentDescription = "search filters icon",
                        tint = Color.DarkGray
                    )
                },
                label = {
                    Text(
                        text = "procurar contrato",
                        fontWeight = FontWeight.Bold
                    ) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    focusedIndicatorColor = DER_yellow,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.Gray,
                    backgroundColor = Color.DarkGray
                ),
                singleLine = true,
                maxLines = 1,
            )
        }




        Column(
            modifier = Modifier
                .height(layoutHeight)
                .width(layoutHeight),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .size(52.dp),
                backgroundColor = DER_yellow,
                shape = RoundedCornerShape(5.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp),
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }

            }
        }


    }

}