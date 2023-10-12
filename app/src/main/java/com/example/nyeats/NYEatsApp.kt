package com.example.nyeats

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
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

/*
    Note:
        - refactor into multiple files?
        - replace hardcoded color and dimen values
 */

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

    val uiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = ScreenName.Categories.name,
        modifier = modifier
    ) {
        composable(route = ScreenName.Categories.name) {
            CategoryScreen(
                onCategoryClicked = {
                    viewModel.updateCurrentCategory(it)
                    navController.navigate(ScreenName.Locations.name)
                },
                modifier = modifier
            )
        }
        composable(route = ScreenName.Locations.name) {
            LocationsScreen(
                category = uiState.currentCategory,
                onLocationClicked = {
                    viewModel.updateCurrentLocation(it)
                    navController.navigate(ScreenName.Detail.name)
                },
                onBackButtonClicked = {navController.popBackStack()})
        }
        composable(route = ScreenName.Detail.name) {
            DetailsScreen(
                location = uiState.currentLocation,
                onBackButtonClicked = {navController.popBackStack()}
            )
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
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .heightIn(max = 150.dp)
        ) {
            Image(
                painter = painterResource(id = category.backgroundImage),
                contentDescription = null,
                contentScale= ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            )
            Text(
                text = stringResource(id = category.name),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
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
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing)),
        contentPadding = PaddingValues(20.dp),
        modifier = modifier
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
        onClick = onCategoryClicked,
        modifier = modifier
    )
}

/**
 * Displays everything for the locations screen: LocationsList,
 */
@Composable
fun LocationsScreen(
    category: Category,
    onLocationClicked: (Location) -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column() {
        LocationsList(list = category.list, onClick = onLocationClicked)
        BackButton(onClick = onBackButtonClicked)
    }
}

/**
 * Displays info for a single location, including name, description, and image.
 */
@Composable
fun DetailsScreen(
    location: Location,
    onBackButtonClicked: () -> Unit,
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
        BackButton(onClick = onBackButtonClicked)
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
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .heightIn(max = 100.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier,
            ) {
                val imageBrush = ShaderBrush(ImageShader(ImageBitmap.imageResource(id = location.image)))
                Canvas(
                    // todo: find way to not zoom in image so much
                    modifier = Modifier.size(100.dp),
                    onDraw = {
                        translate(left = -50f) {
                            scale(scale = 2f) {
                                drawCircle(imageBrush)
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.padding(24.dp))
            Text(
                text = stringResource(id = location.name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = null
        )
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
        DetailsScreen(location = coffeeShops[0], onBackButtonClicked = {})
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    val category = Category(
        name = R.string.coffee_shops,
        icon = R.drawable.ic_coffee,
        backgroundImage = R.drawable.felixroastingco,
        list = listOf()
    )
    NYEatsTheme {
        CategoryItem(category = category, onCategoryClicked = {} )
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