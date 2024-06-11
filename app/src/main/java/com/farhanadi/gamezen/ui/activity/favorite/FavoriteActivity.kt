package com.farhanadi.gamezen.ui.activity.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.farhanadi.gamezen.data.ResultData
import com.farhanadi.gamezen.dependency_injection.InjectionManager
import com.farhanadi.gamezen.ui.ViewModelFactory
import com.farhanadi.gamezen.R
import com.farhanadi.gamezen.data.data_game.Game_Property
import com.farhanadi.gamezen.ui.additional.EmptyList
import com.farhanadi.gamezen.ui.activity.home.ListGame

@Composable
fun FavoriteActivity(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(factory = ViewModelFactory(InjectionManager.provideRepository()))
) {
    val resultdata by rememberUpdatedState(newValue = viewModel.resultdata.collectAsState(initial = ResultData.Loading).value)
    //rememberUpdatedState(newValue = viewModel.resultdata.collectAsState(initial = Result_Data.Loading).value)

    when (resultdata) {
        is ResultData.Loading -> viewModel.getFavoriteGame()
        is ResultData.Success -> FavoriteInformation(
            listGame = (resultdata as ResultData.Success<List<Game_Property>>).data,
            navigateToDetail = navigateToDetail,
            onFavoriteIconClicked = { id, newState -> viewModel.updateGame(id, newState) }
        )
        is ResultData.Error -> {}
    }
}

@Composable
fun FavoriteInformation(
    listGame: List<Game_Property>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit
) {
    Column {
        if (listGame.isNotEmpty()) {
            ListGame(
                listGame = listGame,
                onFavoriteIconClicked = onFavoriteIconClicked,
                contentPaddingTop = 16.dp,
                onCardClicked = navigateToDetail
            )
        } else {
            EmptyList(Warning = stringResource(R.string.if_favorite_is_empty))
        }
    }
}
