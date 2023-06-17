package com.pratwib.consumzy2.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratwib.consumzy2.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbAccount)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}