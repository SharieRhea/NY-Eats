package com.example.nyeats

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nyeats.model.CategoriesRepository.categories
import com.example.nyeats.model.Category
import com.example.nyeats.model.Location
import com.example.nyeats.model.LocationsRepository.coffeeShops
import com.example.nyeats.ui.theme.NYEatsTheme

// Initialize a viewModel for all functions to use. todo: better to be passed into functions?
val viewModel = NYEatsViewModel()

/**
 * Enums for the various types of screens in the NYEats app.
 */
enum class ScreenName(@StringRes val title: Int) {
    Categories(R.string.categories_screen),
    Locations(R.string.locations_screen),
    Detail(R.string.details_screen)
}

/**
 * The main composable for the NYEats app. Handles navigation and uiState.
 */
@Composable
fun NYEats(modifier: Modifier = Modifier) {
    // Initialize a navHostController for navigation
    val navController: NavHostController = rememberNavController()
    // Initialize a backStack for backwards navigation
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Default screen should always be categories, with category and location starting as null
    val currentScreen = ScreenName.valueOf(backStackEntry?.destination?.route ?: ScreenName.Categories.name)

    val uiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = currentScreen.name,
        modifier = Modifier
    ) {
        composable(route = ScreenName.Categories.name) {
            CategoryScreen(
                onCategoryClicked = {
                    viewModel.updateCurrentCategory(it)
                    navController.navigate(ScreenName.Locations.name)
                }
            )
        }
        composable(route = ScreenName.Locations.name) {
            LocationsScreen(
                category = uiState.currentCategory!!,
                onLocationClicked = {
                    viewModel.updateCurrentLocation(it)
                    navController.navigate(ScreenName.Detail.name)
                })
        }
        composable(route = ScreenName.Detail.name) {
            DetailsScreen(location = uiState.currentLocation!!)
        }
    }
}

/**
 * A category card to display an icon and name.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    category: Category,
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        onClick = { onCategoryClicked(category) },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.category_card_inner_padding))
        ) {
            Icon(
                painter = painterResource(id = category.icon),
                contentDescription = null,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.category_card_inner_padding))
            )
            Text(
                text = stringResource(id = category.name),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

/**
 * Lazy column of the full list of category cards.
 */
@Composable
fun CategoriesList(
    list: List<Category>,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing))
    ) {
        items(list) {
            CategoryItem(
                category = it,
                onCategoryClicked = onClick,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

/**
 * Displays everything for the categories screen: CategoriesList,
 */
@Composable
fun CategoryScreen(
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    CategoriesList(
        list = categories,
        onClick = onCategoryClicked
    )
}

/**
 * Displays everything for the locations screen: LocationsList,
 */
@Composable
fun LocationsScreen(
    category: Category,
    onLocationClicked: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    LocationsList(list = category.list, onClick = onLocationClicked)
}

/**
 * Displays info for a single location, including name, description, and image.
 */
@Composable
fun DetailsScreen(
    location: Location,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.details_screen_spacing))
    ) {
        Image(
            painter = painterResource(id = location.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = location.name),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.details_screen_spacing))
        )
        Text(
            text = stringResource(id = location.description),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.details_screen_spacing),
                    end = dimensionResource(id = R.dimen.details_screen_spacing)
                )
        )
    }
}

/**
 * Lazy column of the full list of location cards.
 */
@Composable
fun LocationsList(
    list: List<Location>,
    onClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing))
    ) {
        items(list) {
            LocationItem(
                location = it,
                onLocationClicked = onClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

/**
 * A location card to display an small image and name.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationItem(
    location: Location,
    onLocationClicked: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onLocationClicked(location) },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.category_card_inner_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = location.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.category_card_inner_padding))
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(id = location.name),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NYEatsPreview() {
    NYEatsTheme {
        NYEats()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    NYEatsTheme {
        DetailsScreen(location = coffeeShops[0])
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    NYEatsTheme {
        CategoryItem(category = categories[0], onCategoryClicked = {} )
    }
}

@Preview
@Composable
fun LocationItemPreview() {
    NYEatsTheme {
        LocationItem(location = coffeeShops[0], {})
    }
}

@Preview
@Composable
fun ListsPreview() {
    NYEatsTheme {
        LocationsList(list = coffeeShops, {})
    }
}