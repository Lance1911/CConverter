package com.example.cconverter

import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface CurrencyConverterAPI {

    @GET("api/latest.json?app_id=703af7dc5acd47c3826e155bfed34c9b")
    fun getCurrencyObject(): Call<CurrencyObject>

    @GET("api/currencies.json")
    fun getCurrencyNames(): Call<CurrencyNames>
}