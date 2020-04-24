package com.example.cookeasy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.Data.Ingredient

class IngredientViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.ingredient_item, parent, false)) {
    private val ingredientNameView: TextView = itemView.findViewById(R.id.ingredient_Name)

    fun bind(ingredient: Ingredient) {
        ingredientNameView.text = ingredient.name
    }

}

class IngredientAdapter(private val list: ArrayList<Ingredient>)
    : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IngredientViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient: Ingredient = list[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int = list.size

}