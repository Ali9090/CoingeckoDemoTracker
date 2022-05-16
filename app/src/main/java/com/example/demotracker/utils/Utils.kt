package com.example.demotracker.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.demotracker.model.Coin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception
import kotlin.coroutines.resumeWithException


class Utils {

    companion object {

        suspend fun fetchCoins(context: Context, pageNumber: Int, sortingOrder : String): ArrayList<Coin> =
            suspendCancellableCoroutine { continuation ->
                val requestQueue: RequestQueue? = Volley.newRequestQueue(context)
                var coinList: ArrayList<Coin> = ArrayList()
                val request =
                    JsonArrayRequest(Request.Method.GET, APIHelper.getCoins(pageNumber, sortingOrder), null, Response.Listener { response ->
                        try {
                            val gson = Gson()
                            coinList = gson.fromJson(
                                response.toString(),
                                object : TypeToken<ArrayList<Coin?>?>() {}.type
                            )
                            continuation.resume(coinList, null)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }, Response.ErrorListener { error ->
                        error.printStackTrace()
                        continuation.resumeWithException(error)
                    })
                requestQueue?.add(request)
            }
    }
}