package com.example.cookeasy.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.activities.IngredientsActivity
import com.example.cookeasy.objects.IngredientItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.ingredient_item.view.*
import android.content.Context.LAYOUT_INFLATER_SERVICE
import androidx.core.content.ContextCompat.getSystemService



class IngredientsAdapter(private val ingredientList: ArrayList<IngredientItem>) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(com.example.cookeasy.R.layout.ingredient_item, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun getItemCount() = ingredientList.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = ingredientList[position]
        holder.textView.text = currentItem.name
        holder.quantityTextView.text = currentItem.quantity.toString()

        holder.button.setOnClickListener {
            deleteIngredientFromDatabase(currentItem.name)
            index = holder.adapterPosition
            ingredientList.removeAt(index)
            notifyItemRemoved(index)
        }

//        holder.button2.setOnClickListener {
//            val dialogView = LayoutInflater.from((IngredientsActivity)context).inflate(com.example.cookeasy.R.layout.enter_ingredient, null)
//            editItemInDatabase(currentItem.name)
//            holder.quantityTextView.text =
//        }
    }

    fun removeItem() {
        ingredientList.removeAt(index)
        notifyItemRemoved(index)
    }

    private fun deleteIngredientFromDatabase(item: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val query = FirebaseDatabase.getInstance().getReference("/ingredients/$uid").orderByChild("name").equalTo(item.toString())

        query?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    snapshot.ref.removeValue()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun editItemInDatabase(item: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val query = FirebaseDatabase.getInstance().getReference("/ingredients/$uid").orderByChild("name").equalTo(item.toString())

        query?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    snapshot.ref.child("quantity").setValue("")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.itemName
        val quantityTextView = itemView.itemQuantity
        val button: Button = itemView.deleteButton
//        val button2: Button = itemView.editButton
    }
}