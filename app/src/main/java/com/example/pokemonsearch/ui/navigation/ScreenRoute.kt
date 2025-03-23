package com.example.pokemonsearch.ui.navigation

import com.example.pokemonsearch.model.Pokemon
import kotlinx.serialization.Serializable

sealed class ScreenRoute {
    @Serializable
    data object Splash : ScreenRoute()

    @Serializable
    data object Home : ScreenRoute()

    @Serializable
    data class PokemonDetail(val pokemon: Pokemon) : ScreenRoute()
}