package com.kaiquetavares.jokenpokemon.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaiquetavares.jokenpokemon.R
import com.kaiquetavares.jokenpokemon.model.Jogador
import kotlinx.android.synthetic.main.ranking_card.view.*

class RankingAdapter(private val jogadores: List <Jogador>,
                     val context: Context,
                     val listener: (Jogador) -> Unit): RecyclerView.Adapter <RankingAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jogadorPosition = jogadores[position]
        holder?.let{
            it.bindView(jogadorPosition,listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(
                R.layout.ranking_card,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return jogadores.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView (jogador : Jogador,
                      listener: (Jogador) -> Unit) = with(itemView){
            val tvNome = tvNome
            val tvTitulo = tvTitulo
            val tvPontos = tvPontos

            //Para colocar imagem do usuario
            //ivHeroi.setImageDrawable(ContextCompat.getDrawable(context,heroi.resourceId))
            tvNome.text = jogador.nome
            tvPontos.text = jogador.pontos.toString()
            tvTitulo.text = "PONTUAÇÃO"

            setOnClickListener{listener(jogador)}
        }
    }


}