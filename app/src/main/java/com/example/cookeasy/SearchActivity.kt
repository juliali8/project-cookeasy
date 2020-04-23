package com.example.cookeasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.cookeasy.ViewPagerAdapter
import com.example.cookeasy.R
import com.example.cookeasy.activities.FavActivity
import com.example.cookeasy.activities.GroceriesActivity
import com.example.cookeasy.activities.IngredientsActivity
import com.example.cookeasy.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_recipe.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

    }
    override fun onStart() {
        super.onStart()

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
            R.id.favMenuItem -> {
                val intent = Intent(this, FavActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.groceryMenuItem ->{
                val intent = Intent(this, GroceriesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.searchMenuItem ->{
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
