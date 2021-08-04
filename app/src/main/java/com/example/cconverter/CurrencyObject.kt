package com.example.cconverter

import androidx.room.Entity

@Entity()
data class CurrencyObject(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Rates,
    val timestamp: Int
) {

}