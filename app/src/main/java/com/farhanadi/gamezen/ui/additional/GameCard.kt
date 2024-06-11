package com.farhanadi.gamezen.ui.additional

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farhanadi.gamezen.R

@Composable
fun GameCard(
    id: Int,
    name: String,
    genre: String,
    image: Int,
    gametype: String,
    price: Double,
    isFavGame: Boolean,
    onCardClicked: (Int) -> Unit,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFA7A1BC),
    imagePaddingTop: Dp = 25.dp,
    imagePaddingButtom: Dp = 25.dp,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.clickable { onCardClicked(id) }
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "image_fish",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(backgroundColor)
                    .padding(top = imagePaddingTop, bottom = imagePaddingButtom)
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Divider(modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.height(5.dp))

                CardInfoRow(
                    label = "Genre :",
                    value = genre
                )

                Spacer(modifier = Modifier.height(5.dp))

                CardInfoRow(
                    label = "Jenis Game :",
                    value = gametype
                )

                Spacer(modifier = Modifier.height(2.dp))
                CardInfoRow(
                    label = "Harga :",
                    value = "Rp ${price.toString()}"
                )

                Divider(modifier = Modifier.padding(horizontal = 0.dp, vertical = 10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    val favoriteIcon =
                        if (isFavGame) R.drawable.if_user_already_additemfav else R.drawable.default_favorite
                    val backgroundColor =
                        if (isFavGame) Color(0xFF18122B) else Color.White
                    Text(
                        text = if (isFavGame) "Favorite Game" else "",
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .background(backgroundColor, shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )

                    Image(
                        painter = painterResource(id = favoriteIcon),
                        contentDescription = "Favorite Icon",
                        modifier = Modifier.size(35.dp).clickable { onFavoriteIconClicked(id, !isFavGame) }
                    )
                }
            }
        }
    }
}

@Composable
fun CardInfoRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.body2,
        )
    }
}
