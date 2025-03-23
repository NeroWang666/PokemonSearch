package com.example.pokemonsearch.repository

import com.example.pokemonsearch.model.Pokemonspecies
import com.example.pokemonsearch.service.GraphQLRequest
import com.example.pokemonsearch.service.PokemonApiService
import javax.inject.Inject

const val PAGE_SIZE = 10

class PokemonRepository @Inject constructor(
    private val service: PokemonApiService
) {
    suspend fun searchSpecies(
        keyword: String,
        pageSize: Int = PAGE_SIZE,
        page: Int = 0,
    ): List<Pokemonspecies> {
        val variables = mapOf(
            "query" to "%$keyword%",
            "limit" to pageSize,
            "offset" to pageSize * page,
        )

        val response = service.getPokemonData(
            GraphQLRequest(
                variables = variables,
            )
        )
        return response.body()?.data?.pokemonList ?: emptyList()
    }
}