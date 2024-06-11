package com.farhanadi.gamezen.ui.activity.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.farhanadi.gamezen.data.ResultData
import com.farhanadi.gamezen.dependency_injection.InjectionManager
import com.farhanadi.gamezen.ui.ViewModelFactory
import com.farhanadi.gamezen.R
import com.farhanadi.gamezen.ui.theme.DarkPurple
import com.farhanadi.gamezen.ui.theme.LightPurple


@Composable
fun DetailActivity(
    gameId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(InjectionManager.provideRepository())
    )
) {
    viewModel.resultdata.collectAsState(initial = ResultData.Loading).value.let { uiState ->
        when (uiState) {
            is ResultData.Loading -> {
                viewModel.getGamebyId(gameId)
            }
            is ResultData.Success -> {
                val data = uiState.data
                DetailInformation(
                    image = data.image,
                    id = data.id,
                    name = data.name,
                    genre = data.genre,
                    gametype = data.gametype,
                    publishers = data.publishers,
                    description = data.description,
                    apprelease = data.apprelease,
                    price = data.price,
                    isFavGame = data.isFavGame,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateGame(id, state)
                    }
                )
            }
            is ResultData.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    name: String,
    genre: String,
    @DrawableRes image: Int,
    description: String,
    gametype: String,
    publishers: String,
    apprelease: String,
    price: Double,
    isFavGame: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(250.dp)
                    .height(350.dp)
                    .background(color = LightPurple)
                    .clip(MaterialTheme.shapes.medium)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painter = painterResource(id = if (!isFavGame) R.drawable.default_favorite else R.drawable.if_user_already_additemfav),
                    contentDescription = if (!isFavGame) stringResource(R.string.add_item_to_favorite) else stringResource(R.string.delete_item_from_favoritelist),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .padding(5.dp)
                        .background(Color.Transparent)
                        .clickable {
                            onFavoriteButtonClicked(id, isFavGame)
                        }
                )

            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            DetailInfoRow(
                label = "Harga :",
                value = "Rp ${price.toString()}"
            )

            DetailInfoRow(
                label = "Jenis Game :",
                value = gametype
            )

            DetailInfoRow(
                label = "Genre Game :",
                value = genre
            )

            DetailInfoRow(
                label = "Publishers :",
                value = publishers
            )

            DetailInfoRow(
                label = "Tahun Rilis :",
                value = apprelease
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))

            Text(
                text = description,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = stringResource(R.string.back_text),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(MaterialTheme.colors.primary)
                    .clickable { navigateBack() }
            )
        }


    }

}

@Composable
fun DetailInfoRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.body2,
        )
    }
}


