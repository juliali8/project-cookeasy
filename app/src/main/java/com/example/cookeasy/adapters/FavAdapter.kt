package com.example.cookeasy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.objects.GroceryItem
import com.example.cookeasy.objects.IngredientItem
import com.example.cookeasy.objects.RecipeItem
import kotlinx.android.synthetic.main.ingredient_item.view.*

class FavAdapter(private val recipeList: ArrayList<RecipeItem>) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    private var index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return FavViewHolder(itemView)
    }

    override fun getItemCount() = recipeList.size

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val currentItem = recipeList[position]
        holder.textView.text = currentItem.name

        holder.button.setOnClickListener {
            index = holder.adapterPosition
            recipeList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun removeItem() {
        recipeList.removeAt(index)
        notifyItemRemoved(index)
    }

    class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.itemName
        val button: Button = itemView.deleteButton
    }
}