package com.example.nyeats

import com.example.nyeats.model.CategoriesRepository.categories
import com.example.nyeats.model.Category
import com.example.nyeats.model.Location
import com.example.nyeats.model.LocationsRepository.coffeeShops

/**
 * Data class for UiState variables.
 */
data class NYEatsUiState(
    val currentCategory: Category = categories[0],
    val currentLocation: Location = coffeeShops[0]
)
