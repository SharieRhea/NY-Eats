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
            name = R.string.abraco,
            description = R.string.abraco_description,
            image = R.drawable.abraco
        ),
        Location(
            name = R.string.sey_coffee,
            description = R.string.sey_coffee_description,
            image = R.drawable.seycoffee
        ),
        Location(
            name = R.string.mudspot,
            description = R.string.mudspot_description,
            image = R.drawable.mudspot
        ),
        Location(
            name = R.string.devocion,
            description = R.string.devocion_description,
            image = R.drawable.devocion
        ),
        Location(
            name = R.string.felix_roasting_co,
            description = R.string.felix_roasting_co_description,
            image = R.drawable.felixroastingco
        )
    )

    /**
     * Initializes a list of fast food places.
     */
    val fastFood = listOf(
        Location(
            name = R.string.shake_shack,
            description = R.string.shake_shack_description,
            image = R.drawable.shakeshack
        ),
        Location(
            name = R.string.sweetgreen,
            description = R.string.sweetgreen_description,
            image = R.drawable.sweetgreen
        ),
        Location(
            name = R.string.rays_pizza,
            description = R.string.rays_pizza_description,
            image = R.drawable.rayspizza
        ),
        Location(
            name = R.string.wafels_and_dinges,
            description = R.string.wafels_and_dinges_description,
            image = R.drawable.wafelsanddinges
        ),
        Location(
            name = R.string.nathans_famous,
            description = R.string.nathans_famous_description,
            image = R.drawable.nathansfamous
        )
    )

    /**
     * Initializes a list of restaurants.
     */
    val restaurants = listOf(
        Location(
            name = R.string.gage_and_tollner,
            description = R.string.gage_and_tollner_description,
            image = R.drawable.gage_tollner
        ),
        Location(
            name = R.string.crown_shy,
            description = R.string.crown_shy_description,
            image = R.drawable.crownshy
        ),
        Location(
            name = R.string.tatiana,
            description = R.string.tatiana_description,
            image = R.drawable.tatiana
        ),
        Location(
            name = R.string.gramercy_tavern,
            description = R.string.gramercy_tavern_description,
            image = R.drawable.gramercy_tavern
        ),
        Location(
            name = R.string.dhamaka,
            description = R.string.dhamaka_description,
            image = R.drawable.dhamaka
        )
    )
}