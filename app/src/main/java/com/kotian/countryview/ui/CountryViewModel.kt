package com.kotian.countryview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


import androidx.lifecycle.viewModelScope
import com.kotian.countryview.data.CountryRepository
import com.kotian.countryview.model.Country
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {
    private val _state = MutableStateFlow<UIState>(UIState.Loading)
    val state: StateFlow<UIState> = _state

    init {
        loadCountries()
    }

    fun loadCountries() = viewModelScope.launch {
        _state.value = UIState.Loading
        repository.getCountries()
            .onSuccess { countries ->
                _state.value = if (countries.isEmpty()) UIState.Empty
                else UIState.Success(countries)
            }
            .onFailure { e ->
                _state.value = UIState.Error(e.message ?: "Unknown error occurred")
            }
    }

    sealed class UIState {
        object Loading : UIState()
        object Empty : UIState()
        data class Success(val countries: List<Country>) : UIState()
        data class Error(val message: String) : UIState()
    }
}