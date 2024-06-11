package com.farhanadi.gamezen.ui.activity.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.farhanadi.gamezen.R
import com.farhanadi.gamezen.data.ResultData
import com.farhanadi.gamezen.data.data_game.Game_Property
import com.farhanadi.gamezen.ui.additional.GameCard
import com.farhanadi.gamezen.dependency_injection.InjectionManager
import com.farhanadi.gamezen.ui.additional.SearchButton
import com.farhanadi.gamezen.ui.ViewModelFactory
import com.farhanadi.gamezen.ui.additional.EmptyList
import com.farhanadi.gamezen.ui.theme.DarkPurple
import com.farhanadi.gamezen.ui.theme.LightPurple
import kotlinx.coroutines.launch

@Composable
fun HomeActivity(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(InjectionManager.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.resultdata.collectAsState(initial = ResultData.Loading).value.let { ResultData ->
        when (ResultData) {
            is ResultData.Loading -> {
                viewModel.searchGame(query)
            }
            is ResultData.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::searchGame,
                    listGame = ResultData.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateGame(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is ResultData.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listGame: List<Game_Property>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SearchButton(
            query = query,
            onQueryChange = onQueryChange
        )
        Spacer(modifier = Modifier.height(16.dp))

        // LazyRow for Categories
        CategoriesRow(
            categories = listOf("Action", "Action RPG", "Adventure", "Strategy", "Racing", "Fighting"),
            onCategoryClicked = { category ->
                // Handle category click by updating the selectedCategory state
                selectedCategory = category
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display the games based on the selected category
        if (listGame.isNotEmpty()) {
            val filteredList = selectedCategory?.let { category ->
                // Filter the list based on the selected category
                listGame.filter { it.genre == category }
            } ?: listGame

            ListGame(
                listGame = filteredList,
                onFavoriteIconClicked = onFavoriteIconClicked,
                onCardClicked = navigateToDetail,
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.if_data_is_empty),
                modifier = Modifier.testTag("emptyList")
            )
        }
    }
}

@Composable
fun CategoriesRow(
    categories: List<String>,
    onCategoryClicked: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category, onCategoryClicked = onCategoryClicked)
        }
    }
}

@Composable
fun CategoryItem(category: String, onCategoryClicked: (String) -> Unit) {
    // You can customize the appearance of each category item
    Box(
        modifier = Modifier
            .background(LightPurple)
            .clickable { onCategoryClicked(category) }
            .padding(8.dp)
    ) {
        Text(
            text = category,
            color = DarkPurple,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val circleColor = LightPurple // Replace with the color you want

    FilledIconButton(
        onClick = onClick,
        modifier = modifier
            .background(circleColor, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
            tint = LightPurple // Set the desired color for the arrow icon
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGame(
    listGame: List<Game_Property>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    onCardClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingTop: Dp = 0.dp,
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        // Wrap LazyColumn content in a Column
        val columnModifier = Modifier.background(Color.White).testTag("lazy_list")
        Column(
            modifier = if (contentPaddingTop > 0.dp) columnModifier.padding(top = contentPaddingTop) else columnModifier
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listGame, key = { it.id }) { item ->
                    GameCard(
                        id = item.id,
                        name = item.name,
                        genre = item.genre,
                        image = item.image,
                        gametype = item.gametype,
                        price = item.price,
                        isFavGame = item.isFavGame,
                        onCardClicked = onCardClicked,
                        onFavoriteIconClicked = onFavoriteIconClicked,
                        modifier = Modifier
                            .animateItemPlacement(tween(durationMillis = 200))
                            .clickable { onCardClicked(item.id) }
                    )
                }
            }
        }

        // Apply .align to the Box
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}
