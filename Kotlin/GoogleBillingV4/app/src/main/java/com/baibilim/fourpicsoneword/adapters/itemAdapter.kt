package com.baibilim.fourpicsoneword.adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.billingclient.api.SkuDetails
import com.baibilim.fourpicsoneword.R


class itemAdapter(var list: List<SkuDetails>, var context: Context, var activity: Activity) :
    RecyclerView.Adapter<itemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.clicks.text = item.description
        holder.btn_buy.text = item.price
        Log.d("itemA", " " + list.size)
        Log.d("itemA", " " + item.price)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clicks: TextView
        var btn_buy: Button

        init {
            clicks = itemView.findViewById(R.id.clickss)
            btn_buy = itemView.findViewById(R.id.btn_buy)
        }
    }
}