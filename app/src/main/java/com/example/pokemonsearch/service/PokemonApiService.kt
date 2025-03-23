package com.example.pokemonsearch.service

import com.example.pokemonsearch.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PokemonApiService {
    @POST("graphql/v1beta")
    suspend fun getPokemonData(
        @Body request: GraphQLRequest
    ): Response<GraphQLResponse<PokemonResponse>>
}

data class GraphQLRequest(
    val query: String = POKEMON_QUERY,
    val variables: Map<String, Any>? = null
)

data class GraphQLResponse<T>(
    val data: T
)