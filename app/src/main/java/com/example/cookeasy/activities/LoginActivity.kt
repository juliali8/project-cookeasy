package com.example.cookeasy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookeasy.adapters.LoginPagerAdapter
import com.example.cookeasy.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragmentAdapter = LoginPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)
    }


}
