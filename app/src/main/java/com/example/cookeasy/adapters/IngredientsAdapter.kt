package com.example.cookeasy.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import com.example.cookeasy.objects.GroceryItem
import kotlinx.android.synthetic.main.update_ingredient.*
import kotlinx.android.synthetic.main.update_ingredient.view.*


class IngredientsAdapter(private val context: Context, private val ingredientList: ArrayList<IngredientItem>) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

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

        holder.editButton.setOnClickListener {
            index = holder.adapterPosition
            val dialogView = LayoutInflater.from(context).inflate(R.layout.update_ingredient, null)
            val builder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Edit the Ingredient Item")
            val oldIngredientInput = currentItem.name
            dialogView.name.setText(currentItem.name)
            dialogView.quantity.setText(currentItem.quantity)
            val alertDialog = builder.show()

            alertDialog.updateItem.setOnClickListener {
                val ingredientInput = dialogView.name.text.toString()
                val quantityInput = dialogView.quantity.text.toString()
                if (ingredientInput != "" && quantityInput != "") {
                    val item = IngredientItem(ingredientInput, quantityInput)
                    ingredientList.set(index, item)
                    updateIngredientItem(oldIngredientInput, item)
                    notifyDataSetChanged()
                    alertDialog.dismiss()
                }
            }

            alertDialog.exit.setOnClickListener {
                alertDialog.dismiss()
            }
        }


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

    private fun updateIngredientItem(oldInput: String, ingredientItem: IngredientItem) {

        val uid = FirebaseAuth.getInstance().uid?: ""
        val query = FirebaseDatabase.getInstance().getReference("/ingredients/$uid").orderByChild("name").equalTo(oldInput)

        query?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    snapshot.ref.child("name").setValue(ingredientItem.name)
                    snapshot.ref.child("quantity").setValue(ingredientItem.quantity)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.itemName
        val quantityTextView: TextView = itemView.itemQuantity
        val button: Button = itemView.deleteButton
        val editButton: Button = itemView.editIngredientItem
    }
}