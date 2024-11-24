package com.example.receitaslasalle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


data class Recipe(
    val id: ULong = 0u,
    val name: String = "",
    val image: String = "",
    val author: User = User(),
    val description: String = "",
    val text: String = "",
    val ingredients: MutableList<String> = mutableListOf(),
    val steps: MutableList<String> = mutableListOf(),

)


class MyAdapter(private var dataList: List<Recipe>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = dataList[position]
        holder.textViewName.text = recipe.name
        holder.textViewDescription.text = recipe.description
    }

    override fun getItemCount(): Int = dataList.size

    fun updateData(newDataList: List<Recipe>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}