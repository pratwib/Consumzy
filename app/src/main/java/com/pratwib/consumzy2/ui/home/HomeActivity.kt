package com.pratwib.consumzy2.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratwib.consumzy2.MyApplication
import com.pratwib.consumzy2.R
import com.pratwib.consumzy2.adapter.FoodProfileAdapter
import com.pratwib.consumzy2.database.FoodProfile
import com.pratwib.consumzy2.databinding.ActivityHomeBinding
import com.pratwib.consumzy2.ui.profile.ProfileActivity
import com.pratwib.consumzy2.ui.scan.ScanActivity


@Suppress("UNUSED_EXPRESSION")
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var list = mutableListOf<FoodProfile>()

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        bottomNavigation()
        fabAddList()
    }

    override fun onResume() {
        super.onResume()

        getListFoodProfile()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getListFoodProfile() {
        val adapter = FoodProfileAdapter(this, list)
        binding.rvFoodProfile.adapter = adapter
        binding.rvFoodProfile.layoutManager = LinearLayoutManager(this)
        homeViewModel.getAllFoodProfile().observe(this) {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun fabAddList() {
        binding.fabAddFood.setOnClickListener {
            val addFoodIntent = Intent(this, AddFoodActivity::class.java)
            startActivity(addFoodIntent)
        }
    }


    private fun setupToolbar() {
        setSupportActionBar(binding.tbHome)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val searchIntent = Intent(this, SearchActivity::class.java)
                startActivity(searchIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun bottomNavigation() {
        val navView = binding.navView
        navView.selectedItemId = R.id.nav_home
        navView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> true

                R.id.nav_scan -> {
                    startActivity(Intent(this, ScanActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
            }
            false
        }
    }
}