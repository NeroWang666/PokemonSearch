package com.example.pokemonsearch.model

import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

object PokemonNavType : NavType<Pokemon>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Pokemon? {
        return BundleCompat.getSerializable(bundle, key, Pokemon::class.java)
    }

    override fun parseValue(value: String): Pokemon {
        return Json.decodeFromString<Pokemon>(value)
    }

    override fun put(bundle: Bundle, key: String, value: Pokemon) {
        bundle.putSerializable(key, value)
    }
}
