package com.example.nyeats.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.nyeats.R

/**
 * Data class for a Location. A Location is one establishment in a given category. A location has a
 * name, description, and image.
 */
data class Location(
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int
    )

/**
 * Initializes a list of coffee shops.
 */
object LocationsRepository {
    val coffeeShops = listOf(
        Location(
            name = R.string.brew_brothers,
            description = R.string.brew_brothers_description,
            image = R.drawable.brew_brothers
        ),
        Location(
            name = R.string.daily_grind,
            description = R.string.daily_grind_description,
            image = R.drawable.daily_grind
        ),
        Location(
            name = R.string.latte_lounge,
            description = R.string.latte_lounge_description,
            image = R.drawable.latte_lounge
        ),
        Location(
            name = R.string.maple_cafe,
            description = R.string.maple_cafe_description,
            image = R.drawable.maple_cafe
        ),
        Location(
            name = R.string.the_roast,
            description = R.string.the_roast_description,
            image = R.drawable.the_roast
        )
    )

    /**
     * Initializes a list of fast food places.
     */
    val fastFood = listOf(
        Location(
            name = R.string.just_burgers,
            description = R.string.just_burgers_description,
            image = R.drawable.just_burgers
        ),
        Location(
            name = R.string.links_famous,
            description = R.string.links_famous_description,
            image = R.drawable.links_famous
        ),
        Location(
            name = R.string.wacdonalds,
            description = R.string.wacdonalds_description,
            image = R.drawable.wacdonalds
        ),
        Location(
            name = R.string.the_hot_oven,
            description = R.string.the_hot_oven_description,
            image = R.drawable.the_hot_oven
        ),
        Location(
            name = R.string.snack_shack,
            description = R.string.snack_shack_description,
            image = R.drawable.snack_shack
        )
    )

    /**
     * Initializes a list of restaurants.
     */
    val restaurants = listOf(
        Location(
            name = R.string.the_tower,
            description = R.string.the_tower_description,
            image = R.drawable.the_tower
        ),
        Location(
            name = R.string.painted_pavilion,
            description = R.string.painted_pavilion_description,
            image = R.drawable.painted_pavilion
        ),
        Location(
            name = R.string.mellow_crown,
            description = R.string.mellow_crown_description,
            image = R.drawable.mellow_crown
        ),
        Location(
            name = R.string.good_earth,
            description = R.string.good_earth_description,
            image = R.drawable.good_earth
        ),
        Location(
            name = R.string.elementary,
            description = R.string.elementary_description,
            image = R.drawable.elementary
        )
    )
}