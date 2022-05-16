package com.example.demotracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demotracker.databinding.AdapterCoinBinding
import com.example.demotracker.model.Coin

class CoinsAdapter(private val onSelect: (Coin?) -> Unit): RecyclerView.Adapter<MainViewHolder>() {
    var coins = mutableListOf<Coin>()
    fun setCoinsList(coins: List<Coin>) {
        this.coins = coins.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCoinBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val coin = coins[position]
        holder.binding.nameTextview.text = coin.name
        holder.binding.priceTextview.text = coin.price.toString() + " EUR"
        Glide.with(holder.itemView.context).load(coin.image).into(holder.binding.imageview)
        holder.bind(coin, onSelect)
    }
    override fun getItemCount(): Int {
        return coins.size
    }
}
class MainViewHolder(val binding: AdapterCoinBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(yourDataType: Coin?, onSelect: (Coin?) -> Unit) {
        binding.root.setOnClickListener {
            onSelect(yourDataType)
        }
    }
}