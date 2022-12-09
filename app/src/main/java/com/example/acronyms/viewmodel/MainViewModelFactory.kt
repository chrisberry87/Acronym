package com.example.acronyms.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.acronyms.repository.AcromineApi

class MainViewModelFactory constructor(private val apiService: AcromineApi): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.apiService) as T
        } else {
            throw IllegalArgumentException("MainViewmodel Not Found")
        }
    }
}