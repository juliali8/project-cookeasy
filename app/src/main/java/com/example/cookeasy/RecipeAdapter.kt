package com.example.cookeasy

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy
.Data.Recipe

class RecipeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recipe_item, parent, false)) {
    private val recipeNameView: TextView = itemView.findViewById(R.id.recipe_Name)
    //private val artistImage: ImageView = itemView.findViewById(R.id.artist_image)

    fun bind(recipe: Recipe) {
        recipeNameView.text = recipe.title
    }
//    fun bindImage(artist: Artist){
//        Picasso.get()
//            .load(artist.picture_small)
//            .into(artistImage)
//    }

    fun setClickListener(recipe: Recipe, activity: Activity?) {
        recipeNameView.setOnClickListener {
            //extract recipe info
            var recipeTitle = recipe.title
            var recipeImage = recipe.image
            var recipeServings = recipe.servings
            var recipeLength = recipe.readyInMinutes
            var recipeHealthScore = recipe.healthScore

            println(recipeTitle)

            var context = recipeNameView.context

            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra("recipeTitle", recipeTitle)
            intent.putExtra("recipeImage", recipeImage)
            intent.putExtra("recipeServings", recipeServings)
            intent.putExtra("recipeLength", recipeLength)
            intent.putExtra("recipeHealthScore", recipeHealthScore)
            context.startActivity(intent)
            println("clicked")
        }
    }
}
class RecipeAdapter(private val list: ArrayList<Recipe>, private val activity: Activity?)
    : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe: Recipe = list[position]
        holder.bind(recipe)
        //holder.bindImage(artist)
        holder.setClickListener(recipe, activity)
    }

    override fun getItemCount(): Int = list.size

}
