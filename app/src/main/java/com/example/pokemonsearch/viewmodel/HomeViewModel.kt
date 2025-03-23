package com.example.pokemonsearch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonsearch.model.Pokemonspecies
import com.example.pokemonsearch.model.Result
import com.example.pokemonsearch.repository.PAGE_SIZE
import com.example.pokemonsearch.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    var pokemonList by mutableStateOf<Result<MutableList<Pokemonspecies>>>(Result.Success(mutableListOf()))

    private var keyword = ""
    private var currentPage = 0

    fun searchPokemon(keyword: String) {
        if (keyword.isBlank()) return

        this.keyword = keyword
        currentPage = 0

        viewModelScope.launch(Dispatchers.IO) {
            pokemonList = Result.Loading(
                data = pokemonList.data(),
                isLoadingMore = false,
            )

            runCatching {
                repository.searchSpecies(keyword = keyword)
            }.onSuccess {
                pokemonList = Result.Success(
                    data = it.toMutableStateList(),
                    hasMore = it.size >= PAGE_SIZE
                )
            }.onFailure {
                pokemonList = Result.Error(exception = it)
            }
        }
    }

    fun refresh() = searchPokemon(keyword = keyword)

    fun loadNextPage() {
        currentPage++
        viewModelScope.launch(Dispatchers.IO) {
            val currentData = pokemonList.data()

            pokemonList = Result.Loading(
                data = currentData,
                isLoadingMore = true,
            )

            runCatching {
                repository.searchSpecies(
                    keyword = keyword,
                    page = currentPage,
                )
            }.onSuccess {
                pokemonList = Result.Success(
                    data = currentData?.apply {
                        addAll(it)
                    } ?: it.toMutableStateList(),
                    hasMore = it.size >= PAGE_SIZE,
                )
            }.onFailure {
                pokemonList = Result.Error(data = currentData, exception = it)
            }
        }
    }
}
