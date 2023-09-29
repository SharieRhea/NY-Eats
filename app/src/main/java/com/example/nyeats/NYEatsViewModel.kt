package com.example.nyeats

import androidx.lifecycle.ViewModel
import com.example.nyeats.model.Category
import com.example.nyeats.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NYEatsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NYEatsUiState())
    val uiState: StateFlow<NYEatsUiState> = _uiState


    // yourViewModel.category.observe(viewLifecycleOwner) { category -> {  will be invoked once category changed }

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value = NYEatsUiState()
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }

    fun updateCurrentLocation(location: Location) {
        _uiState.update {
            it.copy(
                currentLocation = location
            )
        }
    }

}