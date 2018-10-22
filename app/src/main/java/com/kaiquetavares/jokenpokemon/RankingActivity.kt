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
        //Dando um GET na api como eu fiz no meu JokenPokemonService
        service.buscarRanking()
                .enqueue(object : Callback<List<Jogador>> {
                    override fun onFailure(call: Call<List<Jogador>>?, t: Throwable?) {
                        Toast.makeText(this@RankingActivity,
                                "Falha na Captura do Dado",
                                Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<List<Jogador>>?, response: Response<List<Jogador>>?) {
                        //Estou recebendo meu JSON e estou colocando ele em um formato de segurança dentro de um LET
                        response?.body()?.let {
                            //Logo meus jogadores vai ser uma lista de objeto Jogador(Model deste projeto) e ele vai ser igual ao IT
                            //O it vai ser o response?.body()?.let que trouxe da API
                            val jogadores: List<Jogador> = it
                            //Irei configurar minha lista passando a minha lista ja preenchida com tudo certo
                            configureList(jogadores)
                        }
                    }
                })
    }

    //Faço uma função que retorna uma lista preenchida
    fun configureList(jogadores: List<Jogador>){
        //Pego meu recycler view e deixo em uma variavel
        val recyclerView = rvRanking
        //Pego meu adapter e irei preencher ele com minha lista de jogadores que coletei da API no OnResponse
        //Meu adapter tem um listener logo eu preciso executar alguma coisa se não vai dar erro, neste caso estou dando um Debug ao usuario
        recyclerView.adapter = RankingAdapter(jogadores, this,{
            Toast.makeText(this,"O jogador: " + it.nome + " tem um total de " + it.pontos + " pontos.",Toast.LENGTH_LONG).show()
        })
        //Falando que meu layout vai ser linear nesta tela
        recyclerView.layoutManager=LinearLayoutManager(this)
    }

    //Codigo realizado por KaiqueTavares :) You´re Welcome.
}




