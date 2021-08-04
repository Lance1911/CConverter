package com.example.cconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_item.view.*

class recyclerAdapter(private val newList: ArrayList<CurrencyItem>, private val listener: OnCurrencyClickListener):
    RecyclerView.Adapter<recyclerAdapter.recyclerViewHolder>() {


    interface OnCurrencyClickListener {
        fun onCurrencyClickListener(position: Int) {

        }
    }

    inner class recyclerViewHolder(textView: View): RecyclerView.ViewHolder(textView), View.OnClickListener {

        val flag = textView.imageView
        val currencyName = textView.titleView
        val baserate = textView.baseRate
        init {
            textView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCurrencyClickListener(position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerAdapter.recyclerViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        return recyclerViewHolder(textView)
    }

    override fun onBindViewHolder(holder: recyclerAdapter.recyclerViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.flag.setImageResource(currentItem.flagId)
        holder.currencyName.text = currentItem.currencyName
        holder.baserate.text = currentItem.rate.toString()

    }

    override fun getItemCount(): Int {
        return newList.size
    }


}