package com.example.demotracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.demotracker.model.Coin
import com.example.demotracker.utils.Constants
import com.example.demotracker.utils.Utils


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val list: MutableLiveData<ArrayList<Coin>> = MutableLiveData<ArrayList<Coin>>()
    var clickedCoin: MutableLiveData<Coin>? = MutableLiveData()
    var order: MutableLiveData<String> = MutableLiveData()

    var pageNumber: Int = 1
    var currentList: ArrayList<Coin> = ArrayList<Coin>()

    init {
        order.postValue(Constants.MARKET_CAP)
    }

    suspend fun fetchCoins() {
        currentList.addAll(
            Utils.fetchCoins(
                getApplication<Application>().applicationContext,
                pageNumber,
                order.value.toString()
            )
        )
        list.postValue(currentList)
        pageNumber++
    }

    fun fetchCoinDetails(coin: Coin) {
        clickedCoin?.postValue(coin)
    }

    fun onMarketCapOrderSelected() {

        if (!order.value.equals(Constants.MARKET_CAP)) {
            currentList.clear()
            order.postValue(Constants.MARKET_CAP)
            pageNumber = 1
        }
    }

    fun popularityOrderSelected() {
        if (!order.value.equals(Constants.POPULARITY)) {
            currentList.clear()
            order.postValue(Constants.POPULARITY)
            pageNumber = 1
        }
    }
}