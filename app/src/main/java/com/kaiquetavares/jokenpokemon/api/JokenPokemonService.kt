package com.kaiquetavares.jokenpokemon.api

import com.kaiquetavares.jokenpokemon.model.Jogador
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface JokenPokemonService {

    @POST("/jokenpokemon/pontuacao")
    fun enviarPontuacao(@Body pontuacao: Jogador): Call<Void>

    @GET("/jokenpokemon/pontuacao")
    fun buscarRanking(): Call<List<Jogador>>
}