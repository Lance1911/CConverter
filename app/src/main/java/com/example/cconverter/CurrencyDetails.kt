package com.example.cconverter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.currency_details.*


class CurrencyDetails: Fragment(R.layout.currency_details), recyclerAdapter.OnCurrencyClickListener {

    lateinit var activityRetriever: Context
    private val cvm: CurrencyViewModel by activityViewModels()
    private var newList: ArrayList<CurrencyItem> = ArrayList()
    private var observingPinnedList: Boolean = false
    private var observingAlphabetSort: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityRetriever = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.adapter = recyclerAdapter(cvm.getCurrencyItems(), this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        observingPinnedList = false
        observingAlphabetSort = false


        sortName.setOnClickListener {
            observingPinnedList = false
            observingAlphabetSort = true
            newList.clear()
            for (i in 0 until cvm.getCurrencyItems().size) {
                val currencyBlock = CurrencyItem(cvm.getCurrencyItems()[i].flagId,
                    cvm.getCurrencyItems()[i].currencyName, cvm.getCurrencyItems()[i].rate)
                newList.add(currencyBlock)
            }
            if (!newList.isEmpty() || newList.size >= 2) {
                for (count in 1 until newList.count()) {
                    // println(items)
                    val item = newList[count]
                    var i = count
                    while (i > 0 && item.currencyName < newList[i - 1].currencyName) {
                        newList[i] = newList[i - 1]
                        i -= 1
                    }
                    newList[i] = item
                }
            }

            recyclerView.adapter = recyclerAdapter(newList, this)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
        }

        reload.setOnClickListener {
            observingPinnedList = false
            observingAlphabetSort = false
            recyclerView.adapter = recyclerAdapter(cvm.getCurrencyItems(), this)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
        }

        pinned.setOnClickListener {
            observingPinnedList = true
            recyclerView.adapter =
                cvm.getPinnedItemsList().value?.let { it1 -> recyclerAdapter(it1, this) }
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
        }
    }


    override fun onCurrencyClickListener(position: Int) {
        if (!observingPinnedList) {
            if (observingAlphabetSort) {
                if (cvm.hasCurrencyItem(newList[position])) {
                    cvm.removeFromPinned(newList[position])
                    Toast.makeText(
                        activityRetriever, newList[position].currencyName +
                                " has been unpinned", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    cvm.addToPinned(newList[position])
                    Toast.makeText(
                        activityRetriever, newList[position].currencyName +
                                " has been pinned", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                if (cvm.hasCurrencyItem(cvm.getCurrencyItems()[position])) {
                    cvm.removeFromPinned(cvm.getCurrencyItems()[position])
                    Toast.makeText(
                        activityRetriever, cvm.getCurrencyItems()[position].currencyName +
                                " has been unpinned", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    cvm.addToPinned(cvm.getCurrencyItems()[position])
                    Toast.makeText(
                        activityRetriever, cvm.getCurrencyItems()[position].currencyName +
                                " has been pinned", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}