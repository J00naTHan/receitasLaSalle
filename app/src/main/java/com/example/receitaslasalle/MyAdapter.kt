package com.example.receitaslasalle

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val dataList:List<String>): RecyclerView.Adapter:<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewByID(R.id.textViewItem)
    }
    override fun on CreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayouInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder (view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewItem.text = dataList[position]
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

}