package com.example.cookeasy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.objects.IngredientItem
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(private val ingredientList: ArrayList<IngredientItem>) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun getItemCount() = ingredientList.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = ingredientList[position]
        holder.textView.text = currentItem.name

        holder.button.setOnClickListener {
            index = holder.adapterPosition
            ingredientList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun removeItem() {
        ingredientList.removeAt(index)
        notifyItemRemoved(index)
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.itemName
        val button: Button = itemView.deleteButton
    }
}