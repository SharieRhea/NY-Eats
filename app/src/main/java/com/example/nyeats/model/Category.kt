package com.example.nyeats.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.nyeats.R
import com.example.nyeats.model.LocationsRepository.coffeeShops
import com.example.nyeats.model.LocationsRepository.fastFood
import com.example.nyeats.model.LocationsRepository.restaurants

data class Category(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val list: List<Location>
)

object CategoriesRepository {
    val categories = listOf(
        Category(
            name = R.string.coffee_shops,
            icon = R.drawable.ic_coffee,
            list = coffeeShops
        ),
        Category(
            name = R.string.fast_food,
            icon = R.drawable.ic_fastfood,
            list = fastFood
        ),
        Category(
            name = R.string.restaurants,
            icon = R.drawable.ic_restaurant,
            list = restaurants
        )
    )
}