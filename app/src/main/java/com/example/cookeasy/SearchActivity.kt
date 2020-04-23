package com.example.cookeasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookeasy.ViewPagerAdapter
import com.example.cookeasy.R
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
}