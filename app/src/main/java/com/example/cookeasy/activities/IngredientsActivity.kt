package com.example.cookeasy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.adapters.IngredientsAdapter
import com.example.cookeasy.objects.IngredientItem
import com.example.cookeasy.objects.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ingredients.*

class IngredientsActivity : AppCompatActivity() {

    var users: User? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var leaderList = ArrayList<User>()

    private lateinit var rView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)

        database = Firebase.database.reference

        val uid = FirebaseAuth.getInstance().currentUser!!.uid


        generateIngredientList(250)
    }


    private fun generateIngredientList(size: Int) {
        val list = ArrayList<IngredientItem>()
        val nameList = ArrayList<String>()

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = FirebaseDatabase.getInstance().getReference("/ingredients")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//               val adapter = IngredientsAdapter<RecyclerView.ViewHolder>()


                p0.children.forEach {
                    Log.d("in foreach", "hi")
                    Log.d("ingredient", it.child("ingredient").getValue().toString())
                    val ingredient = it.child("ingredient").getValue()
                    val item = IngredientItem(ingredient.toString())
                    list.add(item)
                }

                recyclerView.adapter = IngredientsAdapter(list)
                recyclerView.layoutManager = LinearLayoutManager(this@IngredientsActivity)
                recyclerView.setHasFixedSize(true)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

//        return list
    }

}
