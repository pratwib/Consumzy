package com.pratwib.consumzy2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratwib.consumzy2.FoodRepository
import com.pratwib.consumzy2.database.FoodProfile

class HomeViewModel(private val foodRepository: FoodRepository) : ViewModel() {
    fun getAllFoodProfile(): LiveData<List<FoodProfile>> = foodRepository.getAllFoodProfile()
}

class HomeViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}