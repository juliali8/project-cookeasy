package com.example.cookeasy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R
import com.example.cookeasy.objects.FavoriteItem
import com.example.cookeasy.objects.GroceryItem
import com.example.cookeasy.objects.IngredientItem
import com.example.cookeasy.objects.RecipeItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.favorite_item.view.*
import kotlinx.android.synthetic.main.ingredientlist_item.view.*
import kotlinx.android.synthetic.main.ingredientlist_item.view.deleteButton
import kotlinx.android.synthetic.main.ingredientlist_item.view.itemName

class FavAdapter(private val favoriteList: ArrayList<FavoriteItem>) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    private var index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return FavViewHolder(itemView)
    }

    override fun getItemCount() = favoriteList.size

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val currentItem = favoriteList[position]
        holder.textView.text = currentItem.name

        holder.button.setOnClickListener {
            deleteFavoriteFromDatabase(currentItem.name)
            index = holder.adapterPosition
            favoriteList.removeAt(index)
            notifyDataSetChanged()
        }
    }

    private fun deleteFavoriteFromDatabase(item: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val query = FirebaseDatabase.getInstance().getReference("/favorites/$uid").orderByChild("name").equalTo(item)

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

    fun removeItem() {
        favoriteList.removeAt(index)
        notifyItemRemoved(index)
    }

    class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.favItemName
        val button: Button = itemView.deleteButton
    }
}