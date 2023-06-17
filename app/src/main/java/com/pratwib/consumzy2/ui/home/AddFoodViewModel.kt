package com.pratwib.consumzy2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pratwib.consumzy2.FoodRepository
import com.pratwib.consumzy2.database.Category
import com.pratwib.consumzy2.database.Food
import kotlinx.coroutines.launch

class AddFoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {
    fun insertFood(food: Food) {
        viewModelScope.launch {
            foodRepository.insertFood(food)
        }
    }

    fun getAllCategory(): LiveData<List<Category>> = foodRepository.getAllCategory()
}

class AddFoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddFoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}