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

        rvRanking.adapter = RankingAdapter(getJogadores(),this,{
            Toast.makeText(this,"O jogador: " + it.nome + "tem um total de pontos: " + it.pontos,Toast.LENGTH_LONG).show()
        })
        rvRanking.layoutManager= LinearLayoutManager(this)

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
                        val jogador = response?.body()

                    }
                })



        /*
       val service = retrofit.create(JokenPokemonService::class.java!!)
       service.enviarPontuacao(pontuacao)
               .enqueue(object : Callback<Void> {
                   override fun onFailure(call: Call<Void>?, t: Throwable?) {

                   }

                   override fun onResponse(call: Call<Void>?, response: Response<Void>?) {

                   }
               })*/
    }

    //Faço uma função que retorna uma lista preenchida
    fun getJogadores(): List<Jogador>{
        return listOf(
                Jogador("Chiquito",1000)
        )
    }
}




