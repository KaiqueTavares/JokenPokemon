package com.kaiquetavares.jokenpokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.kaiquetavares.jokenpokemon.adapter.RankingAdapter
import com.kaiquetavares.jokenpokemon.api.JokenPokemonService
import com.kaiquetavares.jokenpokemon.model.Jogador
import kotlinx.android.synthetic.main.activity_ranking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        //val pontuacao = Jogador("Felipe Melo", 500)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gamestjd.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(JokenPokemonService::class.java!!)
        service.buscarRanking()
                .enqueue(object : Callback<List<Jogador>> {
                    override fun onFailure(call: Call<List<Jogador>>?, t: Throwable?) {
                        Toast.makeText(this@RankingActivity,
                                "Falha na Captura do Dado",
                                Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<List<Jogador>>?, response: Response<List<Jogador>>?) {
                        response?.body()?.let {
                            val jogadores: List<Jogador> = it
                            configureList(jogadores)
                        }
                    }
                })
    }

    //Faço uma função que retorna uma lista preenchida
    fun configureList(jogadores: List<Jogador>){
        val recyclerView = rvRanking
        recyclerView.adapter = RankingAdapter(jogadores, this,{
            Toast.makeText(this,"O jogador: " + it.nome + " tem um total de " + it.pontos + " pontos.",Toast.LENGTH_LONG).show()
        })
        recyclerView.layoutManager=LinearLayoutManager(this)
    }


}




