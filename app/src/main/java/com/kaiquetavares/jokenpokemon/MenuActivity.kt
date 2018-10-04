package com.kaiquetavares.jokenpokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnJogar.setOnClickListener {
            val Game = Intent(this,GameActivity::class.java)
            startActivity(Game)
        }

        btnRanking.setOnClickListener {
            val Ranking = Intent(this,RankingActivity::class.java)
            startActivity(Ranking)
        }

        btnSobre.setOnClickListener {
            val Sobre = Intent(this,SobreActivity::class.java)
            startActivity(Sobre)
        }

        btnSair.setOnClickListener {
            finish()
        }
    }
}
