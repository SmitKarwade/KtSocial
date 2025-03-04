package com.example.ktinsta.main

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ktinsta.itemfragments.*
import com.example.instagram.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById(R.id.bottomNav)

        val customView = LayoutInflater.from(this).inflate(R.layout.custom_item_layout, null)
        val imageView: ImageView = customView.findViewById(R.id.cstm_user)
        bottomNavigationView.menu.findItem(R.id.cstm_icon).actionView = customView


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.insta_home -> loadFragment(HomeFragment())
                R.id.search -> loadFragment(SearchFragment())
                R.id.add -> loadFragment(AddFragment())
                R.id.reels -> loadFragment(ReelFragment())
                R.id.cstm_icon -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
