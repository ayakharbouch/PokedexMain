package com.aya.pokedex.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.aya.pokedex.models.Pokemon;
import com.aya.pokedex.models.PokemonRespuesta;

/**
 * Created by Daniel Alvarez on 28/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
    @GET("{dexNumOrName}/")
    Call<Pokemon> getPokemon(@Path("dexNumOrName") String dexNumOrName);
}
