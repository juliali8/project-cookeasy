package com.example.cookeasy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookeasy.Data.Ingredient
import com.example.cookeasy.Data.Recipe
import com.example.cookeasy.ViewModel.RecipeViewModel
import com.example.cookeasy.activities.FavActivity
import com.example.cookeasy.activities.MainActivity
import com.example.cookeasy.adapters.FavAdapter
import com.example.cookeasy.objects.FavoriteItem
import com.example.cookeasy.objects.GroceryItem
import com.example.cookeasyapi.ViewModel.RecipeIngredientViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_favs.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recipe_info.*
import kotlinx.android.synthetic.main.activity_recipe_info.recyclerView

class RecipeActivity: AppCompatActivity() {
    var ingredientList: ArrayList<Ingredient> = ArrayList()
    var recipeTitle: String = ""
    var recipeServings: Int=0
    var recipeID: Int = 0
    public lateinit var recipeTitleView: TextView
    public lateinit var recipeServingsView: TextView
    public lateinit var recipeReadyTimeView: ImageView
    public lateinit var backButton: Button
    public lateinit var addToFavoriteButton: Button
    lateinit var viewModel: RecipeIngredientViewModel
    private var favoriteList = ArrayList<FavoriteItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_info)

        //set recyclerView
        val recyclerView = recyclerView
        val adapter = IngredientAdapter(ingredientList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Get the info from the main activity
        val intent = intent
        viewModel = ViewModelProviders.of(this).get(RecipeIngredientViewModel::class.java)
        recipeTitle = intent.getStringExtra("recipeTitle")
        recipeServings = intent!!.getIntExtra("recipeServings",0)
        recipeID = intent!!.getIntExtra("recipeID",0)
        viewModel.ingredientList.observe(this, Observer { ingredients ->
            // Update the cached copy of the words in the adapter.
            ingredientList.clear()
            ingredientList.addAll(ingredients.ingredients)
            adapter.notifyDataSetChanged()
        })
        viewModel.getRecipeIngredient(recipeID)

    }
    override fun onStart() {
        super.onStart()

        //Set the view
        recipeTitleView = recipe_Info_Name
        recipeTitleView.text = recipeTitle
        recipeServingsView = recipeInfo_serving
        recipeServingsView.text= "Servings:" + recipeServings
        backButton = back_button
        addToFavoriteButton = add_to_Favorite



        //Set onclick listener for back button
        backButton.setOnClickListener{
            //Intent to rolls activity
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        add_to_Favorite.setOnClickListener {
            val item = FavoriteItem(recipeTitle)
            favoriteList.add(item)
            writeNewFavorite(item)
//            favRecyclerView.adapter = FavAdapter(favoriteList)
//            recyclerView.layoutManager = LinearLayoutManager(FavActivity::class.java)
//            recyclerView.setHasFixedSize(true)
//            val adapter = favRecyclerView.adapter as FavAdapter
//            adapter.notifyDataSetChanged()
        }

    }

    private fun writeNewFavorite(favoriteItem: FavoriteItem) {
        val uid = FirebaseAuth.getInstance().uid?: ""
//        FirebaseDatabase.getInstance().getReference("/users/$uid").setValue(user)
        val newId = FirebaseDatabase.getInstance().getReference("/favorites/$uid").push().key
        FirebaseDatabase.getInstance().getReference("/favorites/$uid/$newId/name").setValue(favoriteItem.name)
    }

}