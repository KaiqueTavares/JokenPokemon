package com.kaiquetavares.jokenpokemon

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {

    private val delayTela = 1000L
    //Criando uma variavel que ela atribui um numero random que pode ser nulo
    private var numeroAleatorio: Random?=null
    var pontos:Int = 0
    var jogadas:Int = 0
    //VARIAVEIS DE CONTROLE DE ESCOLHA DE JOGADA
    private var FOGO = 1
    private var GRAMA = 2
    private var AGUA = 3

    //Funções de Derrota Vitoria e empate
    private fun venceu(){
        //Pego meu TextView Resultado, atribuio um texto onde vou pegar uma string do meu arquivo:
        //values -> strings.xml
        pontos+=2
        jogadas++
        tvResultado!!.text = getString(R.string.venceu)
        //Atribuindo uma cor ao meu texto, onde pego no XML color.
        tvResultado!!.setTextColor(ContextCompat.getColor(this,R.color.vitoria))
        tvPontuacao!!.text = pontos.toString()
        tvTentativas!!.text = jogadas.toString()
    }

    private fun perdeu(){
        jogadas++
        tvResultado!!.text = getString(R.string.derrota)
        tvResultado!!.setTextColor(ContextCompat.getColor(this,R.color.derrota))
        tvTentativas!!.text = jogadas.toString()
        Handler().postDelayed({

            val telaGameOver = Intent(this,GameOverActivity::class.java)
            telaGameOver.putExtra("PONTUACAO", pontos)
            startActivity(telaGameOver)
            finish()
        },delayTela)

    }

    private fun empate(){
        pontos++
        jogadas++
        tvResultado!!.text = getString(R.string.empate)
        tvResultado!!.setTextColor(ContextCompat.getColor(this,R.color.empate))
        tvPontuacao!!.text = pontos.toString()
        tvTentativas!!.text = jogadas.toString()
    }

    //Criando um metodo que sera responsavel por dar as jogadas ao pc e tudo mais
    private fun realizarJogada(jogadaPlayer:Int){
        //Ao chamar minha função eu crio uma variavel que recebe um Media Player (Som), com o audio que escolhi
        //var player = MediaPlayer.create(this,R.raw.jokenpo)
        //Pego a variavel e faço ela tocar.
        //player.start()

        //A jogada PC vai ser uma variavel que recebe o numero aleatorio e que atribui mais 1 pois nossos privates
        //Privates INTs sao 1 2 e 3, mas como falamos que ele vai dar um random em 3 ele retorna (0,1,2)
        var jogadaPC = numeroAleatorio!!.nextInt(3)+1

        //Quando eu tiver qualquer numero do Jogada PC
        when (jogadaPC){
        //Se for pedra, vale lembrar que ele terá retornado 1
            FOGO ->{
                //Troco minha iamgem para a pedra
                ivResultadoPC!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.op_charmander))
                //Qunado eu receber o parametro que eu passar quando chamar a func
                when (jogadaPlayer){
                //Se este parametro for papel ele vence, por que papel embrulha a pedra.
                    GRAMA -> perdeu()
                    FOGO -> empate()
                    AGUA -> venceu()
                }
            }

            GRAMA -> {
                ivResultadoPC!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.op_bulbassaur))
                when(jogadaPlayer){
                    GRAMA -> empate()
                    FOGO -> venceu()
                    AGUA -> perdeu()
                }
            }

            AGUA -> {
                ivResultadoPC!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.op_squirtle))
                when(jogadaPlayer){
                    GRAMA -> venceu()
                    FOGO -> perdeu()
                    AGUA -> empate()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Resetando visualmente o texto de cada textView dos pontos e jogadas
        tvPontuacao!!.text = pontos.toString()
        tvTentativas!!.text = jogadas.toString()

        //Minha variavel recebe um valor random
        numeroAleatorio=Random()

        //Quando o usuario clicar na pedra
        ivFogo.setOnClickListener {
            //Ele irá trocar a imagem para pedra
            ivResultadoPlayer!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.op_charmander))
            //Vou chamar minha função passando o valor de PEDRA para que a função calcule e nos de o resultado.
            realizarJogada(FOGO)
        }

        ivGrama.setOnClickListener {
            ivResultadoPlayer!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.op_bulbassaur))
            realizarJogada(GRAMA)
        }

        ivAgua.setOnClickListener {
            ivResultadoPlayer!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.op_squirtle))
            realizarJogada(AGUA)
        }

    }
}
