package com.example.cconverter

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
class mainFragment: Fragment(R.layout.main_fragment) {
    lateinit var activityRetriever: Context
    private val cvm: CurrencyViewModel by activityViewModels()
    private var newList: ArrayList<Double> = ArrayList()
    private var conversionFactor: Double = 0.00
    private var conversionFactorInt: Int = 0
    private var conversionToFactor: Double = 0.00
    private var name: String = String()
    private var orientation: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityRetriever = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orientation = resources.configuration.orientation

        getCurrencies()
        Log.e("LIST SIZE: ", cvm.getCurrencyItems().size.toString())
        refresh.setOnClickListener {
            getCurrencies()
            //Toast.makeText(context, "Cannot Refresh. Check internet connection and try again", Toast.LENGTH_LONG)
        }
        //getNames()
    }

    /**
     * Retrieves currency exchange rates from the api using retrofit
     */
    fun getCurrencies() {
        val retrofit: Retrofit = Retrofit.Builder().
        baseUrl("https://openexchangerates.org/").
        addConverterFactory(GsonConverterFactory.create()).build()

        var currencyConverterAPI = retrofit.create(CurrencyConverterAPI::class.java)
        var call: Call<CurrencyObject> = currencyConverterAPI.getCurrencyObject()

        call.enqueue(object: Callback<CurrencyObject> {
            override fun onResponse(call: Call<CurrencyObject>, response: Response<CurrencyObject>) {
                if (!response.isSuccessful()) {
                    result.setText(response.code())
                    return
                }

                //
                var rates: ArrayList<Double> = ArrayList()

                var currencyItem: CurrencyObject? = response.body()
                if (currencyItem != null) {
                    rates= arrayListOf(1.0, currencyItem.rates.EUR,
                        currencyItem.rates.JPY, currencyItem.rates.GBP,
                        currencyItem.rates.AUD, currencyItem.rates.CAD,
                        currencyItem.rates.CHF, currencyItem.rates.CNY,
                        currencyItem.rates.HKD, currencyItem.rates.NZD,
                        currencyItem.rates.SEK, currencyItem.rates.KRW,
                        currencyItem.rates.SGD, currencyItem.rates.NOK,
                        currencyItem.rates.MXN, currencyItem.rates.INR,
                        currencyItem.rates.RUB, currencyItem.rates.ZAR,
                        currencyItem.rates.TRY, currencyItem.rates.BRL,
                        currencyItem.rates.TWD, currencyItem.rates.DKK,
                        currencyItem.rates.PLN, currencyItem.rates.THB,
                        currencyItem.rates.IDR, currencyItem.rates.HUF,
                        currencyItem.rates.CZK, currencyItem.rates.ILS,
                        currencyItem.rates.CLP, currencyItem.rates.PHP,
                        currencyItem.rates.AED, currencyItem.rates.COP,
                        currencyItem.rates.SAR, currencyItem.rates.MYR,
                        currencyItem.rates.RON, currencyItem.rates.ARS,
                        currencyItem.rates.VND, currencyItem.rates.BOB)
                }

                cvm.setRates(rates)
                cvm.intializeCurrencyItemArray()
                firstImage.setImageResource(R.drawable.united_states)
                secondImage.setImageResource(R.drawable.united_states)

                listButton.setOnClickListener {
                    view?.findNavController()?.navigate(R.id.currencyDetails)
                }

                val arrayAdapter = context?.let{
                    ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, cvm.getNames())
                }

                spinnerFrom.adapter = arrayAdapter
                spinnerFrom.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p0 != null) {
                            (p0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                        }
                        conversionFactor = rates[p2]
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            firstCurrency.text = cvm.getNames()[p2]
                        }
                        firstImage.setImageResource(cvm.getFlags()[p2])
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

                spinnerTo.adapter = arrayAdapter
                spinnerTo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p0 != null) {
                            (p0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                        }
                        conversionToFactor = rates[p2]
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            secondCurrency.text = cvm.getNames()[p2]
                        }
                        secondImage.setImageResource(cvm.getFlags()[p2])
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }


                convert.setOnClickListener {
                    if (!numberFrom.text.toString().isEmpty()) {
                        var midwayResult: Double =
                            numberFrom.text.toString().toDouble() * conversionToFactor
                        var finalResult: Double = midwayResult / conversionFactor
                        result.text = "%.2f".format(finalResult)
                    }
                    else {
                        Toast.makeText(context, "Please enter a valid currency value", Toast.LENGTH_SHORT)
                    }
                }
            }

            override fun onFailure(call: Call<CurrencyObject>, t: Throwable) {
                t.message?.let { Log.e("LOSER", it) }
            }

        })
    }

}
