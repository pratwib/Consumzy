package com.pratwib.consumzy2.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratwib.consumzy2.FoodRepository
import com.pratwib.consumzy2.database.Category

class CategoryViewModel(private val foodRepository: FoodRepository) : ViewModel() {
    fun getAllCategory(): LiveData<List<Category>> = foodRepository.getAllCategory()
}

class CategoryViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}