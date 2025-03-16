package com.kotian.countryview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotian.countryview.data.CountryRepository

class CountryViewModelFactory(
    private val repository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryViewModel(repository) as T
    }
}