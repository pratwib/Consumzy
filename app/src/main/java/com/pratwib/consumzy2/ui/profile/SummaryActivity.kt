package com.pratwib.consumzy2.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratwib.consumzy2.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbSummary)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}