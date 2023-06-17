package com.pratwib.consumzy2

import android.app.Application
import com.pratwib.consumzy2.database.FoodDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { FoodDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { FoodRepository(database.foodDao()) }
}