package com.example.cookeasy.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.objects.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import android.R.menu
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.*
import android.widget.Toast
import com.example.cookeasy.adapters.GroceriesAdapter
import com.example.cookeasy.objects.GroceryItem
import kotlinx.android.synthetic.main.activity_groceries.*
import kotlinx.android.synthetic.main.enter_grocery.*
import kotlinx.android.synthetic.main.enter_grocery.view.*
import kotlinx.android.synthetic.main.grocery_item.*


class GroceriesActivity : AppCompatActivity() {

    var users: User? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    private lateinit var rView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var groceryList = ArrayList<GroceryItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groceries)

        database = Firebase.database.reference

        val uid = FirebaseAuth.getInstance().currentUser!!.uid


        generateGroceryList(250)
    }


    private fun generateGroceryList(size: Int) {
        val nameList = ArrayList<String>()

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = FirebaseDatabase.getInstance().getReference("/groceries/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {


                p0.children.forEach {
                    Log.d("in foreach", "hi")
                    Log.d("grocery", it.child("name").getValue(String::class.java).toString())
                    val grocery = it.child("name").getValue(String::class.java).toString()
                    if(grocery != null) {
                        val item = GroceryItem(grocery.toString())
                        groceryList.add(item)
                    }
                }

                recyclerView.adapter = GroceriesAdapter(groceryList)
                recyclerView.layoutManager = LinearLayoutManager(this@GroceriesActivity)
                recyclerView.setHasFixedSize(true)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

//        return list
    }

    fun deleteItemFromView(view: View) {
        val index = groceryList.indexOf(GroceryItem(itemName.text.toString()))
        recyclerView.adapter = GroceriesAdapter(groceryList)
        groceryList.remove(GroceryItem(itemName.text.toString()))
        val adapter = recyclerView.adapter as GroceriesAdapter
        adapter.removeItem()
        recyclerView.layoutManager = LinearLayoutManager(this@GroceriesActivity)
        recyclerView.setHasFixedSize(true)
        Log.d("delete", "button clicked!")
        deleteGroceryFromDatabase(itemName.text.toString())
    }

    fun dialogView(view: View) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.enter_grocery, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter the grocery item")
        val alertDialog = builder.show()

        alertDialog.submitItem.setOnClickListener {
            val groceryInput = dialogView.name.text.toString()
            if(groceryInput != ""){
                writeNewGrocery(groceryInput)
                val item = GroceryItem(groceryInput)
                groceryList.add(item)
                recyclerView.adapter = GroceriesAdapter(groceryList)
                recyclerView.layoutManager = LinearLayoutManager(this@GroceriesActivity)
                recyclerView.setHasFixedSize(true)
                alertDialog.dismiss()
            }
        }
    }

    private fun writeNewGrocery(groceryName: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val grocery = GroceryItem(groceryName)
//        FirebaseDatabase.getInstance().getReference("/users/$uid").setValue(user)
        FirebaseDatabase.getInstance().getReference("/groceries/$uid").push().setValue(grocery)
    }

    private fun deleteGroceryFromDatabase(groceryName: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
//        FirebaseDatabase.getInstance().getReference("/groceries/$uid").child("name").removeValue()

//        val grocery = GroceryItem(groceryName)
//        val grocery = it.child("name").getValue(String::class.java).toString()

        val query = FirebaseDatabase.getInstance().getReference("/groceries/$uid").child("name").equalTo(groceryName)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    snapshot.ref.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


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
            R.id.groceryMenuItem ->{
                Toast.makeText(applicationContext, "grocery item clicked", Toast.LENGTH_LONG).show()
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