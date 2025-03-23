package com.example.pokemonsearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pokemonsearch.model.Pokemon
import com.example.pokemonsearch.model.PokemonNavType
import com.example.pokemonsearch.ui.compose.HomeScreen
import com.example.pokemonsearch.ui.compose.PokemonDetailScreen
import com.example.pokemonsearch.ui.compose.SplashScreen
import kotlin.reflect.full.createType

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.Splash) {
        composable<ScreenRoute.Splash> { SplashScreen(navController = navController) }
        composable<ScreenRoute.Home> { HomeScreen(navController = navController) }
        composable<ScreenRoute.PokemonDetail>(
            typeMap = mapOf(Pokemon::class.createType() to PokemonNavType)
        ) { backStackEntry ->
            val pokemon = backStackEntry.toRoute<ScreenRoute.PokemonDetail>().pokemon
            PokemonDetailScreen(navController = navController, pokemon = pokemon)
        }
    }
}