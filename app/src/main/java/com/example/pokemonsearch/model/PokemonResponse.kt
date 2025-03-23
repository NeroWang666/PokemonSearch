package com.example.pokemonsearch.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class PokemonResponse(
    @SerializedName("pokemon_v2_pokemonspecies")
    val pokemonList: List<Pokemonspecies>
)

data class Pokemonspecies(
    val id: Int,
    val name: String,
    @SerializedName("capture_rate")
    val captureRate: Int,
    @SerializedName("pokemon_v2_pokemoncolor")
    val color: PokemonColor,
    @SerializedName("pokemon_v2_pokemons")
    val pokemonList: List<Pokemon>
)

data class PokemonColor(
    val id: Int,
    val name: String
)

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    @SerializedName("pokemon_v2_pokemonabilities")
    val abilities: List<PokemonAbility>
) : java.io.Serializable {

    override fun toString(): String {
        return Json.encodeToString(this)
    }
}

@Serializable
data class PokemonAbility(
    val id: Int,
    @SerializedName("pokemon_v2_ability")
    val ability: Ability
) : java.io.Serializable

@Serializable
data class Ability(
    val name: String
) : java.io.Serializable
