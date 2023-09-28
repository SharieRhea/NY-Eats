package com.example.nyeats.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nyeats.R

data class Category(
    @StringRes val name: Int,
    @DrawableRes val icon: Int
)

object CategoriesRepository {
    val categories = listOf(
        Category(
            name = R.string.coffee_shops,
            icon = R.drawable.ic_coffee
        ),
        Category(
            name = R.string.fast_food,
            icon = R.drawable.ic_fastfood
        ),
        Category(
            name = R.string.restaurants,
            icon = R.drawable.ic_restaurant
        )
    )
}