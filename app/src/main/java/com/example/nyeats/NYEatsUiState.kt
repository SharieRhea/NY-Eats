package com.example.nyeats

import com.example.nyeats.model.Category
import com.example.nyeats.model.Location

data class NYEatsUiState(
    val currentCategory: Category? = null,
    val currentLocation: Location? = null
)
