package com.example.pokemonsearch.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokemonsearch.model.Pokemonspecies
import com.example.pokemonsearch.model.Result
import com.example.pokemonsearch.viewmodel.HomeViewModel

private const val TYPE_ITEM = "TYPE_ITEM"
private const val TYPE_LOAD_MORE = "TYPE_LOAD_MORE"
private const val TYPE_LOADING_MORE = "TYPE_LOADING_MORE"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val result = viewModel.pokemonList

    HomeScreenInternal(
        result = result,
        navController = navController,
        onSearch = {
            if (result !is Result.Loading) {
                viewModel.searchPokemon(keyword = it)
                keyboardController?.hide()
            }
        },
        onRefresh = viewModel::refresh,
        onLoadMore = viewModel::loadNextPage,
    )

    val context = LocalContext.current
    LaunchedEffect(result) {
        if (result is Result.Error) {
            result.exception.message?.run {
                Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
private fun HomeScreenInternal(
    result: Result<List<Pokemonspecies>>,
    navController: NavController = rememberNavController(),
    onSearch: (String) -> Unit,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
) {
    Scaffold(
        topBar = {
            SearchBar(onSearch = onSearch)
        },
    ) {
        PokemonList(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            result = result,
            navController = navController,
            onRefresh = onRefresh,
            onLoadMore = onLoadMore,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(onSearch: (String) -> Unit) {
    var textState by remember { mutableStateOf("") }

    TopAppBar(
        modifier = Modifier.padding(vertical = 8.dp),
        title = {
            TextField(
                value = textState,
                onValueChange = { textState = it.trim() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (textState.isNotBlank()) {
                            onSearch(textState)
                        }
                    }
                )
            )
        },
        actions = {
            Button(
                modifier = Modifier.padding(start = 4.dp),
                enabled = textState.isNotBlank(),
                onClick = {
                    onSearch(textState)
                },
            ) {
                Text(
                    text = "Search"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonList(
    modifier: Modifier,
    result: Result<List<Pokemonspecies>>,
    navController: NavController,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier,
        state = rememberPullToRefreshState(),
        isRefreshing = result is Result.Loading && !result.isLoadingMore,
        onRefresh = onRefresh,
    ) {
        result.data()?.let { items ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    items = items,
                    key = Pokemonspecies::id,
                    contentType = { TYPE_ITEM },
                ) {
                    PokemonListItem(
                        pokemon = it,
                        navController = navController,
                    )
                }

                if (result is Result.Success && result.hasMore) {
                    item(contentType = TYPE_LOAD_MORE) {
                        LaunchedEffect(Unit) {
                            onLoadMore()
                        }
                    }
                }

                if (result is Result.Loading && result.isLoadingMore) {
                    item(contentType = TYPE_LOADING_MORE) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenInternal(
        result = Result.Loading(),
        onSearch = {},
        onRefresh = {},
        onLoadMore = {},
    )
}
