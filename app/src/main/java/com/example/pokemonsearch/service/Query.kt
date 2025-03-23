package com.example.pokemonsearch.service

internal val POKEMON_QUERY = """
    query PokemonQuery(${"$"}query: String!, ${"$"}limit: Int!, ${"$"}offset: Int!) {
        pokemon_v2_pokemonspecies(where: {name: {_ilike: ${"$"}query}}, limit: ${"$"}limit, offset: ${"$"}offset) {
            id
            name
            capture_rate
            pokemon_v2_pokemoncolor {
                id
                name
            }
            pokemon_v2_pokemons {
                id
                name
                pokemon_v2_pokemonabilities {
                    id
                    pokemon_v2_ability {
                        name
                    }
                }
            }
        }
    }
""".trimIndent()