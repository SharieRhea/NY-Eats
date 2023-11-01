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
    @StringRes val contentDescription: Int,
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
            contentDescription = R.string.brew_brothers_content_description,
            image = R.drawable.brew_brothers
        ),
        Location(
            name = R.string.daily_grind,
            description = R.string.daily_grind_description,
            contentDescription = R.string.daily_grind_content_description,
            image = R.drawable.daily_grind
        ),
        Location(
            name = R.string.latte_lounge,
            description = R.string.latte_lounge_description,
            contentDescription = R.string.latte_lounge_content_description,
            image = R.drawable.latte_lounge
        ),
        Location(
            name = R.string.maple_cafe,
            description = R.string.maple_cafe_description,
            contentDescription = R.string.maple_cafe_content_description,
            image = R.drawable.maple_cafe
        ),
        Location(
            name = R.string.the_roast,
            description = R.string.the_roast_description,
            contentDescription = R.string.the_roast_content_description,
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
            contentDescription = R.string.just_burgers_content_description,
            image = R.drawable.just_burgers
        ),
        Location(
            name = R.string.links_famous,
            description = R.string.links_famous_description,
            contentDescription = R.string.links_famous_content_description,
            image = R.drawable.links_famous
        ),
        Location(
            name = R.string.wacdonalds,
            description = R.string.wacdonalds_description,
            contentDescription = R.string.wacdonalds_content_description,
            image = R.drawable.wacdonalds
        ),
        Location(
            name = R.string.the_hot_oven,
            description = R.string.the_hot_oven_description,
            contentDescription = R.string.the_hot_oven_content_description,
            image = R.drawable.the_hot_oven
        ),
        Location(
            name = R.string.snack_shack,
            description = R.string.snack_shack_description,
            contentDescription = R.string.snack_shack_content_description,
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
            contentDescription = R.string.the_tower_content_description,
            image = R.drawable.the_tower
        ),
        Location(
            name = R.string.painted_pavilion,
            description = R.string.painted_pavilion_description,
            contentDescription = R.string.painted_pavilion_content_description,
            image = R.drawable.painted_pavilion
        ),
        Location(
            name = R.string.mellow_crown,
            description = R.string.mellow_crown_description,
            contentDescription = R.string.mellow_crown_content_description,
            image = R.drawable.mellow_crown
        ),
        Location(
            name = R.string.good_earth,
            description = R.string.good_earth_description,
            contentDescription = R.string.good_earth_content_description,
            image = R.drawable.good_earth
        ),
        Location(
            name = R.string.elementary,
            description = R.string.elementary_description,
            contentDescription = R.string.elementary_content_description,
            image = R.drawable.elementary
        )
    )
}