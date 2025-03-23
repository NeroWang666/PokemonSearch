package com.example.pokemonsearch.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokemonsearch.model.Ability
import com.example.pokemonsearch.model.Pokemon
import com.example.pokemonsearch.model.PokemonAbility
import com.example.pokemonsearch.model.PokemonColor
import com.example.pokemonsearch.model.Pokemonspecies
import com.example.pokemonsearch.ui.navigation.ScreenRoute

@Composable
fun PokemonListItem(pokemon: Pokemonspecies, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .let {
                val backgroundColor = remember {
                    runCatching {
                        Color(pokemon.color.name.toColorInt())
                    }.getOrNull()
                }

                if (backgroundColor != null) {
                    it.background(backgroundColor)
                } else {
                    it
                }
            }
            .padding(16.dp)
    ) {
        Text(
            text = "Name: ${pokemon.name}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Capture Rate: ${pokemon.captureRate}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = "Pokémons:",
            style = MaterialTheme.typography.bodyLarge,
        )

        pokemon.pokemonList.forEach { pokemon ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(ScreenRoute.PokemonDetail(pokemon = pokemon))
                    }
                    .padding(vertical = 4.dp),
                text = "- ${pokemon.name}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun PokemonListItemPreview() {
    PokemonListItem(
        navController = rememberNavController(),
        pokemon = Pokemonspecies(
            id = 1,
            name = "test",
            captureRate = 123,
            color = PokemonColor(
                id = 1,
                name = "yellow"
            ),
            pokemonList = listOf(
                Pokemon(
                    id = 10103,
                    name = "vulpix-alola",
                    abilities = listOf(
                        PokemonAbility(
                            id = 2558,
                            ability = Ability(name = "snow-cloak")
                        ),
                        PokemonAbility(
                            id = 2559,
                            ability = Ability(name = "snow-warning")
                        )
                    )
                )
            )
        )
    )
}
