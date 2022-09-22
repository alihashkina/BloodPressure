package com.example.bloodpressure.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressure.R
import com.example.bloodpressure.adapters.dataClass.Card
import com.example.bloodpressure.databinding.CardStatisticsBinding

class CardAdapter: RecyclerView.Adapter<CardAdapter.CardHolder>() {

    companion object{
        val cardList = ArrayList<Card>()
    }

    class CardHolder(item: View): RecyclerView.ViewHolder(item){

        val binding = CardStatisticsBinding.bind(item)

        fun bind(card: Card) = with(binding){
            cardDate.text = card.cardDate
            cardHealthy.text = card.cardHealthy
            cardUnhealthy.text = card.cardUnhealthy
            cardSymptoms.text = card.cardSymptoms
            cardCare.text = card.cardCare
            cardUpper.text = card.cardUpper
            cardLower.text = card.cardLower
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_statistics, parent, false)
        return CardHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun addCard(card: Card){
        cardList.add(card)
        notifyDataSetChanged()
    }
}