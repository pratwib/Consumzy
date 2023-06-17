package com.pratwib.consumzy2.helper

import android.graphics.Color
import com.pratwib.consumzy2.R
import com.pratwib.consumzy2.database.Category

object InitialDataSource {
    fun getCategories(): List<Category> {
        return listOf(
            Category(name = "Meats", color = Color.parseColor("#FF2424")),
            Category(name = "Fruits", color = Color.parseColor("#FC6404")),
            Category(name = "Vegetables", color = Color.parseColor("#8CC43C")),
            Category(name = "Herbs and Spices", color = Color.parseColor("#4C3228")),
            Category(name = "Oils", color = Color.parseColor("#FCD444")),
            Category(name = "Frozen Food", color = Color.parseColor("#5BC0DE")),
            Category(name = "Flours", color = Color.parseColor("#FC8C84")),
            Category(name = "Sauces", color = Color.parseColor("#AC54AB")),
        )
    }
}
