package com.example.demotracker.view

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demotracker.R
import com.example.demotracker.adapter.CoinsAdapter
import com.example.demotracker.databinding.ActivityMainBinding
import com.example.demotracker.model.Coin
import com.example.demotracker.utils.PaginationScrollListener
import com.example.demotracker.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    val adapter = CoinsAdapter() {
        showCoinDetails(it)
    }
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        binding.viewModel = MainViewModel(this.application)
        binding.executePendingBindings()
        binding.coinsRecyclerview.adapter = adapter
        bottomSheetBehavior = BottomSheetBehavior.from(binding.root.findViewById(R.id.bottomSheet))
        setObservers()
        launchCoinsApiCoroutine()
        setPaginationListener()
        setBottomSheetBehavior()
    }

    fun setPaginationListener() {
        binding.coinsRecyclerview?.addOnScrollListener(object :
            PaginationScrollListener(binding.coinsRecyclerview.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                launchCoinsApiCoroutine()
            }
        })
    }

    fun setBottomSheetBehavior() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun populateList(coinList: ArrayList<Coin>) {
        adapter.setCoinsList(coinList)
        isLoading = false
        binding.paginationProgress.visibility = View.GONE
        binding.progressbar.visibility = View.GONE
    }

    fun showCoinDetails(coin: Coin?) {
        var bundle: Bundle = Bundle()
        bundle.putSerializable("selected_coin", coin)

        CoinBottomSheetFragment().apply {
            show(supportFragmentManager, CoinBottomSheetFragment.TAG)
        }.arguments = bundle
    }

    fun launchCoinsApiCoroutine() {
        isLoading = true
        lifecycleScope.launch {
            binding.paginationProgress.visibility = View.VISIBLE
            binding.paginationProgress.bringToFront()
            binding.viewModel?.fetchCoins()
        }
    }

    fun setObservers() {
        val coinObserver = Observer<ArrayList<Coin>> { coinList ->
            populateList(coinList)
        }
        val clickedCoinObserver = Observer<Coin> { clickedCoin ->
            showCoinDetails(clickedCoin)
        }

        val clickedOrderObserver = Observer<String> {
            launchCoinsApiCoroutine()
            binding.progressbar.visibility = View.VISIBLE
            adapter.setCoinsList(ArrayList())
        }
        binding.viewModel?.order?.observe(this, clickedOrderObserver)
        binding.viewModel?.list?.observe(this, coinObserver)
        binding.viewModel?.clickedCoin?.observe(this, clickedCoinObserver)
    }


}