package com.pratwib.consumzy2.ui.scan

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratwib.consumzy2.databinding.ActivityResultBinding
import java.io.File

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val imagePath = intent.getStringExtra("imagePath")
        if (imagePath != null) {
            val imageFile = File(imagePath)
            val bitmap = BitmapFactory.decodeFile(imageFile.path)
            binding.ivResultImage.setImageBitmap(bitmap)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbResult)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}