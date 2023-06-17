package com.pratwib.consumzy2

import androidx.lifecycle.LiveData
import com.pratwib.consumzy2.database.Category
import com.pratwib.consumzy2.database.Food
import com.pratwib.consumzy2.database.FoodDao
import com.pratwib.consumzy2.database.FoodProfile

class FoodRepository(private val foodDao: FoodDao) {
    suspend fun insertFood(food: Food): Unit = foodDao.insertFood(food)
    suspend fun updateFood(food: Food): Unit = foodDao.updateFood(food)
    suspend fun deleteFoodById(foodId: Int): Unit = foodDao.deleteFoodById(foodId)
    fun getAllCategory(): LiveData<List<Category>> = foodDao.getAllCategory()
    fun getAllFoodProfile(): LiveData<List<FoodProfile>> = foodDao.getAllFoodProfile()
}