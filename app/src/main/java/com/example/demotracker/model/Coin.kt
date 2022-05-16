package com.example.demotracker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.nio.channels.FileLock

data class Coin(
    @SerializedName("name")
    var name: String,
    @SerializedName("current_price")
    var price: Double,
    @SerializedName("image")
    var image: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("symbol")
    var symbol: String,
    @SerializedName("market_cap")
    var marketCap: Double,
    @SerializedName("high_24h")
    var high24: Double,
    @SerializedName("low_24h")
    var low24: Double,
    @SerializedName("circulating_supply")
    var circulatingSupply: Double,
    @SerializedName("max_supply")
    var maxSupply: Double
) : Serializable {

}