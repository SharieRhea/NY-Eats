package com.example.nyeats

import androidx.lifecycle.ViewModel
import com.example.nyeats.model.Category
import com.example.nyeats.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * A ViewModel for the NYEats app.
 */
class NYEatsViewModel : ViewModel() {

    // Initialize uiState for the ViewModel to use
    private val _uiState = MutableStateFlow(NYEatsUiState())
    val uiState: StateFlow<NYEatsUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value = NYEatsUiState()
    }

    /**
     * Updates the value of currentCategory.
     */
    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }

    /**
     * Updates the value of currentLocation.
     */
    fun updateCurrentLocation(location: Location) {
        _uiState.update {
            it.copy(
                currentLocation = location
            )
        }
    }

}