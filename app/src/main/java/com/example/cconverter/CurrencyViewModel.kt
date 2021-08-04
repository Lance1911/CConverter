package com.example.cconverter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*


class CurrencyViewModel: ViewModel() {
    private var rates: ArrayList<Double> = ArrayList()
    private var currencyItems: ArrayList<CurrencyItem> = ArrayList()
    private var pinnedItems: ArrayList<CurrencyItem> = ArrayList()
    private var names: ArrayList<String> = arrayListOf("United States Dollar", "Euro", "Japanese Yen", "British Pound Sterling",
        "Australian Dollar", "Canadian Dollar", "Swiss Franc", "Chinese Yuan", "Hong Kong Dollar", "New Zealand Dollar",
        "Swedish Krona", "South Korean Won", "Singapore Dollar", "Norwegian Krone", "Mexican Peso", "Indian Rupee",
        "Russian Ruble", "South African Rand", "Turkish Lira", "Brazilian Real", "New Taiwan Dollar", "Danish Krone",
        "Polish Zloty", "Thai Baht", "Indonesian Rupiah", "Hungarian Forint", "Czech Republic Koruna", "Israeli New Sheqel",
        "Chilean Peso", "Philippine Peso", "United Arab Emirates Dirham", "Colombian Peso", "Saudi Riyal",
        "Malaysian Ringgit", "Romanian Leu", "Argentine Peso", "Vietnamese Dong", "Bolivian Boliviano")

    private var flags: ArrayList<Int> = arrayListOf(R.drawable.united_states, R.drawable.europe,
    R.drawable.japan, R.drawable.britain, R.drawable.australia, R.drawable.canada, R.drawable.swiss,
    R.drawable.china, R.drawable.hongkong, R.drawable.new_zealand, R.drawable.sweden,
    R.drawable.south_korea, R.drawable.singapore, R.drawable.norway, R.drawable.mexico, R.drawable.india,
    R.drawable.russia, R.drawable.south_africa, R.drawable.turkey, R.drawable.brazil, R.drawable.taiwan,
    R.drawable.denmark, R.drawable.poland, R.drawable.thai, R.drawable.indonesia, R.drawable.hungary,
        R.drawable.czech, R.drawable.israel, R.drawable.chile, R.drawable.philippines,
        R.drawable.united_arab_emirates, R.drawable.colombia, R.drawable.saudi_arabia, R.drawable.malaysia,
    R.drawable.romania, R.drawable.argentina, R.drawable.vietnam, R.drawable.bolivia)

    private var pi: MutableLiveData<ArrayList<CurrencyItem>> =
        MutableLiveData<ArrayList<CurrencyItem>>(pinnedItems)

    fun intializeCurrencyItemArray() {
        if (currencyItems.isEmpty()) {
            for (i in 0 until rates.size) {
                var currency = CurrencyItem(flags[i], names[i], rates[i])
                currencyItems.add(currency)
            }
        }
    }

    fun hasCurrencyItem(newItem: CurrencyItem): Boolean {
        for (i in 0 until pinnedItems.size) {
            if (pinnedItems[i].currencyName.equals(newItem.currencyName)) {
                return true
            }
        }

        return false
    }

    fun addToPinned(pinned: CurrencyItem) {
        this.pi.value?.add(pinned)
    }

    fun removeFromPinned(pinned: CurrencyItem) {
        this.pi.value?.remove(pinned)
    }

    fun getPinnedItemsList(): MutableLiveData<ArrayList<CurrencyItem>> {
        return this.pi
    }

    fun getCurrencyItems(): ArrayList<CurrencyItem> {
        return currencyItems
    }
    fun setRates(r: ArrayList<Double>) {
        this.rates.clear()
        this.rates = r
    }

    fun getRates(): ArrayList<Double> {
        return rates
    }

    fun getFlags(): ArrayList<Int> {
        return flags
    }

    fun setNames(n: ArrayList<String>) {
        this.names = n
    }

    fun getNames(): ArrayList<String> {
        return names
    }


}