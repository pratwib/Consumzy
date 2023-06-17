package com.pratwib.consumzy2.database

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val foodId: Int = 0,
    val name: String,
    val quantity: String,
    val cId: Int,
    val expiredDate: Long,
    val place: String,
    val timestamp: Long = 0,
) : Parcelable

@Parcelize
@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val catId: Int = 0,
    val name: String,
    val color: Int,
) : Parcelable

@Parcelize
data class FoodProfile(
    @Embedded val food: Food,
    @Relation(parentColumn = "cId", entityColumn = "catId")
    val category: Category
) : Parcelable