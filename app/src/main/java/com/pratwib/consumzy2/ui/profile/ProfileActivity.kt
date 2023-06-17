package com.pratwib.consumzy2.ui.profile

import android.accounts.Account
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.pratwib.consumzy2.R
import com.pratwib.consumzy2.databinding.ActivityProfileBinding
import com.pratwib.consumzy2.ui.home.HomeActivity
import com.pratwib.consumzy2.ui.scan.ScanActivity

@Suppress("UNUSED_EXPRESSION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardSelection()
        bottomNavigation()
    }

    private fun cardSelection() {
        val cardMyAccount = binding.cvProfileAccount
        cardMyAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
        val cardMySummary = binding.cvProfileSummary
        cardMySummary.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            startActivity(intent)
        }
        val cardMyCategory = binding.cvProfileCategory
        cardMyCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bottomNavigation() {
        val navView = binding.navView
        navView.selectedItemId = R.id.nav_profile
        navView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }

                R.id.nav_scan -> {
                    startActivity(Intent(this@ProfileActivity, ScanActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }

                R.id.nav_profile -> true
            }
            false
        }
    }
}