package com.pratwib.consumzy2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialCategory(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFood(food: Food)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateFood(food: Food)

    @Transaction
    @Query("SELECT * from category")
    fun getAllCategory(): LiveData<List<Category>>

    @Transaction
    @Query("SELECT * from food")
    fun getAllFoodProfile(): LiveData<List<FoodProfile>>

    @Transaction
    @Query("SELECT * from food where foodId = :foodId")
    fun getFoodProfile(foodId: Int): LiveData<FoodProfile>

    @Transaction
    @Query("DELETE FROM food WHERE foodId = :foodId")
    suspend fun deleteFoodById(foodId: Int)
}