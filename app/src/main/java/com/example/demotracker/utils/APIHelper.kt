package com.example.demotracker.utils

class APIHelper {

    companion object{
        val BASE_URL = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&sparkline=false&page=%s&order=%s&sparkline=false"

        fun getCoins(page : Int, sortOrder : String) : String{
            return String.format(BASE_URL, page.toString(), sortOrder);
        }
    }
}