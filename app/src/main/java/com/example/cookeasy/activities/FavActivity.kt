package com.example.cookeasy.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.SearchActivity
import com.example.cookeasy.adapters.FavAdapter
import com.example.cookeasy.adapters.GroceriesAdapter
import com.example.cookeasy.adapters.IngredientsAdapter
import com.example.cookeasy.objects.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_favs.*
import kotlinx.android.synthetic.main.activity_groceries.*
import kotlinx.android.synthetic.main.activity_groceries.recyclerView
import kotlinx.android.synthetic.main.activity_ingredients.*
import kotlinx.android.synthetic.main.enter_ingredient.*
import kotlinx.android.synthetic.main.enter_ingredient.view.*
import kotlinx.android.synthetic.main.grocery_item.*

class FavActivity : AppCompatActivity(){
    var users: User? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    private lateinit var rView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var favList = ArrayList<FavoriteItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs)

        database = Firebase.database.reference

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        generateRecipeList(250)

    }

    private fun generateRecipeList(size: Int) {
        val nameList = ArrayList<String>()

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = FirebaseDatabase.getInstance().getReference("/favorites/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {


                p0.children.forEach {
                    Log.d("in foreach", "hi")
                    Log.d("recipe", it.child("name").getValue(String::class.java).toString())
                    val recipe = it.child("name").getValue(String::class.java).toString()
                    if(recipe != null) {
                        val item = FavoriteItem(recipe.toString())
                        favList.add(item)
                    }
                }

                favRecyclerView.adapter = FavAdapter(favList)
                favRecyclerView.layoutManager = LinearLayoutManager(this@FavActivity)
                favRecyclerView.setHasFixedSize(true)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

//        return list
    }

    fun deleteItemFromView(view: View) {
        val index = favList.indexOf(FavoriteItem(itemName.text.toString()))
        recyclerView.adapter = FavAdapter(favList)
        favList.remove(FavoriteItem(itemName.text.toString()))
        val adapter = recyclerView.adapter as FavAdapter
        adapter.removeItem()
        recyclerView.layoutManager = LinearLayoutManager(this@FavActivity)
        recyclerView.setHasFixedSize(true)
        Log.d("delete", "button clicked!")
    }

    /*fun dialogView(view: View) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.enter_ingredient, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter the ingredient")
        val alertDialog = builder.show()

        alertDialog.submitItem.setOnClickListener {
            val ingredientInput = dialogView.name.text.toString()
            if(ingredientInput != ""){
                writeNewIngredient(ingredientInput)
                val item = IngredientItem(ingredientInput)
                ingredientList.add(item)
                recyclerView.adapter = IngredientsAdapter(ingredientList)
                recyclerView.layoutManager = LinearLayoutManager(this@IngredientsActivity)
                recyclerView.setHasFixedSize(true)
                alertDialog.dismiss()
            }
        }
    }*/

    /*private fun writeNewIngredient(ingredientName: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val ingredient = IngredientItem(ingredientName)
//        FirebaseDatabase.getInstance().getReference("/users/$uid").setValue(user)
        FirebaseDatabase.getInstance().getReference("/ingredients/$uid").push().setValue(ingredient)
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ingredientMenuItem -> {
                val intent = Intent(this, IngredientsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favMenuItem -> {
                Toast.makeText(applicationContext, "fav recipes item clicked", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.groceryMenuItem ->{
                val intent = Intent(this, GroceriesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.searchMenuItem ->{
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.logoutMenuItem ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}