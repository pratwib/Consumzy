package com.pratwib.consumzy2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pratwib.consumzy2.FoodRepository
import com.pratwib.consumzy2.database.Category
import com.pratwib.consumzy2.database.Food
import kotlinx.coroutines.launch

class EditFoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {
    fun updateFood(food: Food) {
        viewModelScope.launch {
            foodRepository.updateFood(food)
        }
    }

    fun deleteFoodById(foodId: Int) {
        viewModelScope.launch {
            foodRepository.deleteFoodById(foodId)
        }
    }

    fun getAllCategory(): LiveData<List<Category>> = foodRepository.getAllCategory()
}

class EditFoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditFoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditFoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}