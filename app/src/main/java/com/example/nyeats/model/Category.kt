package com.example.nyeats.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.nyeats.R
import com.example.nyeats.model.LocationsRepository.coffeeShops
import com.example.nyeats.model.LocationsRepository.fastFood
import com.example.nyeats.model.LocationsRepository.restaurants

/**
 * Data class to hold data for a Category. A Category is a type of place where food and/or drink
 * can be purchased. Each category has a name, icon, and list of locations.
 */
data class Category(
    @StringRes val name: Int,
    @DrawableRes val backgroundImage: Int,
    @StringRes val contentDescription: Int,
    val list: List<Location>
)

/**
 * Initializes lists of locations for each category.
 */
object CategoriesRepository {
    val categories = listOf(
        Category(
            name = R.string.coffee_shops,
            backgroundImage = R.drawable.coffee_shops,
            contentDescription = R.string.coffee_shops_content_description,
            list = coffeeShops
        ),
        Category(
            name = R.string.fast_food,
            backgroundImage = R.drawable.fast_food,
            contentDescription = R.string.fast_food_content_description,
            list = fastFood
        ),
        Category(
            name = R.string.restaurants,
            backgroundImage = R.drawable.restaurant,
            contentDescription = R.string.restaurants_content_description,
            list = restaurants
        )
    )
}