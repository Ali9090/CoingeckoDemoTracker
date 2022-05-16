package com.example.demotracker.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.demotracker.R
import com.example.demotracker.model.Coin
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CoinBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var coin : Coin

    companion object {

        const val TAG = "CoinBottomSheetFragment"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

        coin = this.arguments?.get("selected_coin") as Coin

        val titleTextView : TextView = view.findViewById(R.id.title_textview)
        val priceTextView :TextView = view.findViewById(R.id.current_price_textview)
        val lowTextView :TextView = view.findViewById(R.id.low_textview)
        val highTextView :TextView = view.findViewById(R.id.high_textview)
        val marketCapTextView :TextView = view.findViewById(R.id.market_cap_textview)
        val circulatingSupplyTextView :TextView = view.findViewById(R.id.circulating_textview)
        val maxSupplyTextView :TextView = view.findViewById(R.id.max_supply_textview)
        val shareInfoButton : AppCompatButton = view.findViewById(R.id.share_info_button)

        shareInfoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_info_title))
            intent.putExtra(android.content.Intent.EXTRA_TEXT, coin.toString())
            startActivity(Intent.createChooser(intent, getString(R.string.share_info_title)))
        }

        Glide.with(this).load(coin.image).into(view.findViewById(R.id.imageview))
        titleTextView.text = coin.name

        setText(getString(R.string.max_supply), coin.maxSupply.toString(),maxSupplyTextView)
        setText(getString(R.string.circulating_supply), coin.circulatingSupply.toString(),circulatingSupplyTextView)
        setText(getString(R.string.market_cap), coin.marketCap.toString(),marketCapTextView)
        setText(getString(R.string.high_24), coin.high24.toString(),highTextView)
        setText(getString(R.string.low_24), coin.low24.toString(),lowTextView)
        setText(getString(R.string.current_price), coin.price.toString(),priceTextView)

        return view
    }

    fun setText(descriptionText: String, valueString: String, textView : TextView){
        val builder = SpannableStringBuilder()
        val txtSpannable = SpannableString(descriptionText)
        val boldSpan = StyleSpan(Typeface.BOLD)
        txtSpannable.setSpan(boldSpan, 0, descriptionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(txtSpannable)

        builder.append("\n"+valueString)
        textView.text = builder
    }

}