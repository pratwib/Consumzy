package com.pratwib.consumzy2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pratwib.consumzy2.helper.InitialDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Food::class, Category::class],
    version = 1,
    exportSchema = true
)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): FoodDatabase {
            if (INSTANCE == null) {
                synchronized(FoodDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FoodDatabase::class.java, "food_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    applicationScope.launch {
                                        val foodDao = database.foodDao()
                                        foodDao.insertInitialCategory(InitialDataSource.getCategories())
                                    }
                                }
                            }
                        })
                        .build()
                }
            }
            return INSTANCE as FoodDatabase
        }
    }
}