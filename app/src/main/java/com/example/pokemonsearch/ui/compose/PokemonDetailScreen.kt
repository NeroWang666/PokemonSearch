package com.example.pokemonsearch.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokemonsearch.model.Ability
import com.example.pokemonsearch.model.Pokemon
import com.example.pokemonsearch.model.PokemonAbility

@Composable
fun PokemonDetailScreen(navController: NavHostController, pokemon: Pokemon) {
    Scaffold(
        topBar = {
            TopBar(navController = navController)
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "Name: ${pokemon.name}",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Abilities:",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            items(
                items = pokemon.abilities,
                key = PokemonAbility::id
            ) { item ->
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "- ${item.ability.name}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavHostController) {
    TopAppBar(
        modifier = Modifier.padding(vertical = 8.dp),
        title = {
            Text(
                text = "Pokémon detail",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = navController::popBackStack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@Preview
@Composable
private fun PokemonDetailPreview() {
    PokemonDetailScreen(
        navController = rememberNavController(),
        pokemon = Pokemon(
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
}
