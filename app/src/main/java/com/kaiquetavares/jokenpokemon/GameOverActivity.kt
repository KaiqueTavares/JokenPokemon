package com.kaiquetavares.jokenpokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.kaiquetavares.jokenpokemon.api.JokenPokemonService
import com.kaiquetavares.jokenpokemon.model.Jogador
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameOverActivity : AppCompatActivity() {

    private val delayTela = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        //Recuperando a variavel pontos que foi passada da tela de Game
        val pontos = intent.extras.getInt("PONTUACAO")

        //API Link
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gamestjd.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        btJogarNovamente.setOnClickListener {
            val service = retrofit.create(JokenPokemonService::class.java!!)
            //Usando um POST E passando os parametros necessarios para o objeto mandar a api
            //Vale lembrar que aqui eu peguei o que o usuario colocou como nome e os pontos que tive na outra tela
            service.enviarPontuacao(pontuacao = Jogador(etNomePlayer.text.toString(),pontos))
                    .enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>?, t: Throwable?) {
                            Toast.makeText(this@GameOverActivity,
                                    "Falha no Envio de Dados",
                                    Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                            Toast.makeText(this@GameOverActivity,
                                    "SUCESSO no Envio de Dados",
                                    Toast.LENGTH_SHORT).show()
                        }
                    })
            //Parecido com uma corountine que ira demorar o que coloquei em delay tela para executar a acao
            Handler().postDelayed({
                //Iniciando a tela de game
                val Game = Intent(this,GameActivity::class.java)
                startActivity(Game)
                //Finalizando/Destruindo esta minha tela
                finish()
            },delayTela)
        }

        btVoltarMenu.setOnClickListener{
            val Menu = Intent(this,MenuActivity::class.java)
            startActivity(Menu)
            finish()
        }
    }
}
