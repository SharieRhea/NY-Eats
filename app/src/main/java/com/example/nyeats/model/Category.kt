package com.example.nyeats.model

import androidx.annotation.StringRes
import com.example.nyeats.R

data class Category(
    @StringRes val name: Int,
    // val icon: ImageVector
)

object CategoriesRepository {
    val categories = listOf(
        Category(
            name = R.string.coffee_shops
        ),
        Category(
            name = R.string.fast_food
        ),
        Category(
            name = R.string.restaurants
        )
    )
}