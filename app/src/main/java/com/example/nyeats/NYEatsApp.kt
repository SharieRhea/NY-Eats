package com.example.nyeats

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

// Initialize a viewModel for all functions to use.
val viewModel = NYEatsViewModel()

/**
 * Enums for the various types of screens in the NYEats app.
 */
enum class ScreenName {
    Categories, Locations, Detail,
    CategoriesAndLocations, LocationsAndDetails
}

/**
 * The main composable for the NYEats app. Handles navigation and uiState.
 */
@Composable
fun NYEats(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    // Initialize a navHostController for navigation
    val navController: NavHostController = rememberNavController()
    // Initialize a backStack for backwards navigation (not used within code)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val uiState = viewModel.uiState.collectAsState().value

    if (windowSize == WindowWidthSizeClass.Expanded) {
        CreateExpandedNavHost(navController = navController, uiState = uiState, modifier)
    } else {
        CreateNavHost(navController = navController, uiState = uiState, modifier)
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
    uiState: NYEatsUiState,
    highlighted: Boolean,
) {
    Card(
        onClick = { onCategoryClicked(category) },
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
        modifier = Modifier.width(dimensionResource(id = R.dimen.category_card_max_width))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .heightIn(max = dimensionResource(id = R.dimen.category_card_max_height))
        ) {
            Image(
                painter = painterResource(id = category.backgroundImage),
                contentDescription = stringResource(id = category.contentDescription),
                contentScale= ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background( color = if (highlighted && category == uiState.currentCategory) colorResource(id = R.color.black_transparent_44) else colorResource(id = R.color.black_transparent_77))
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
    uiState: NYEatsUiState,
    highlighted: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.list_item_padding)),
        modifier = modifier
    ) {
        items(list) {
            CategoryItem(
                category = it,
                onCategoryClicked = onClick,
                uiState = uiState,
                highlighted = highlighted
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
    uiState: NYEatsUiState,
    highlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CategoriesList(
            list = categories,
            onClick = onCategoryClicked,
            uiState = uiState,
            highlighted = highlighted,
            modifier = modifier
        )
    }
}

/**
 * Displays both the Categories Screen and Locations Screen for expanded devices.
 */
@Composable
fun CategoriesAndLocationsScreen(
    onCategoryClicked: (Category) -> Unit,
    onLocationClicked: (Location) -> Unit,
    uiState: NYEatsUiState,
    highlightedLocations: Boolean,
    highlightedCategories: Boolean,
    currentCategory: Category,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        CategoryScreen(
            onCategoryClicked = onCategoryClicked,
            highlighted = highlightedCategories,
            uiState = uiState,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        LocationsScreen(
            category = currentCategory,
            onLocationClicked = onLocationClicked,
            uiState = uiState,
            highlighted = highlightedLocations,
            modifier = Modifier
                .fillMaxHeight()
        )
    }
}

/**
 * Displays both the Locations Screen and the Details Screen for expanded devices.
 */
@Composable
fun LocationsAndDetailsScreen(
    onLocationClicked: (Location) -> Unit,
    currentCategory: Category,
    currentLocation: Location,
    uiState: NYEatsUiState,
    highlighted: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        LocationsScreen(
            category = currentCategory,
            onLocationClicked = onLocationClicked,
            uiState = uiState,
            highlighted = highlighted,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.details_screen_spacing)))
        DetailsScreen(
            location = currentLocation
        )
    }
}

/**
 * Displays everything for the locations screen: LocationsList,
 */
@Composable
fun LocationsScreen(
    category: Category,
    onLocationClicked: (Location) -> Unit,
    uiState: NYEatsUiState,
    highlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        LocationsList(
            list = category.list,
            onClick = onLocationClicked,
            uiState = uiState,
            highlighted = highlighted,
            modifier = modifier
        )
    }
}

/**
 * Displays info for a single location, including name, description, and image.
 */
@Composable
fun DetailsScreen(
    location: Location,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.details_screen_spacing)),
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = location.image),
            contentDescription = stringResource(id = location.contentDescription),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = dimensionResource(id = R.dimen.category_card_max_width))
        )
        Text(
            text = stringResource(id = location.name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.details_screen_spacing))
        )
        Text(
            text = stringResource(id = location.description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
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
    uiState: NYEatsUiState,
    highlighted: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.list_item_padding)),
        modifier = modifier
    ) {
        items(list) {
            LocationItem(
                location = it,
                onLocationClicked = onClick,
                highlighted = highlighted,
                uiState = uiState
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
    uiState: NYEatsUiState,
    highlighted: Boolean
) {
    Card(
        onClick = { onLocationClicked(location) },
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
        colors = if (highlighted && location == uiState.currentLocation) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer) else CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .heightIn(max = dimensionResource(id = R.dimen.location_card_max_height))
            .width(dimensionResource(id = R.dimen.location_card_max_width))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val imageBrush =
                        ShaderBrush(ImageShader(ImageBitmap.imageResource(id = location.image)))
                    Canvas(
                        onDraw = {
                            withTransform({
                                // Scale by half to make image size more appropriate
                                scale(0.5f, 0.5f)
                                translate(left = -size.width * 1.15f, top = -size.height * 1.15f)
                            }) {
                                // Center offset is half the size of the image to place center of the circle at the center of the image
                                drawCircle(imageBrush, radius = size.width, center = Offset(x = size.width / 2, y = size.width / 2))
                            }
                        },
                        modifier = Modifier.size(dimensionResource(id = R.dimen.location_card_image_size))
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.location_text_padding)))
                    Text(
                        text = stringResource(id = location.name),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

/**
 * Creates a NavHost with transition animations for Expanded width devices.
 */
@Composable
fun CreateExpandedNavHost(navController: NavHostController, uiState: NYEatsUiState, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = ScreenName.Categories.name,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        modifier = modifier
    ) {
        composable(
            route = ScreenName.Categories.name,
        ) {
            CategoryScreen(
                onCategoryClicked = {
                    viewModel.updateCurrentCategory(it)
                    navController.navigate(ScreenName.CategoriesAndLocations.name)
                },
                uiState = uiState,
                highlighted = false,
                modifier = Modifier.fillMaxHeight()
            )
        }
        composable(
            route = ScreenName.CategoriesAndLocations.name,
        ) {
            CategoriesAndLocationsScreen(
                onCategoryClicked = {
                    viewModel.updateCurrentCategory(it)
                },
                onLocationClicked = {
                    viewModel.updateCurrentLocation(it)
                    navController.navigate(ScreenName.LocationsAndDetails.name)
                },
                currentCategory = uiState.currentCategory,
                uiState = uiState,
                highlightedLocations = false,
                highlightedCategories = true
            )
        }
        composable(
            route = ScreenName.LocationsAndDetails.name
        ) {
            LocationsAndDetailsScreen(
                onLocationClicked = {
                    viewModel.updateCurrentLocation(it)
                },
                currentCategory = uiState.currentCategory,
                currentLocation = uiState.currentLocation,
                uiState = uiState,
                highlighted = true
            )
        }
    }
}

/**
 * Creates a NavHost with transition animations for Compact and Medium width devices.
 */
@Composable
fun CreateNavHost(navController: NavHostController, uiState: NYEatsUiState, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = ScreenName.Categories.name,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Down
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Down
            )
        },
        modifier = modifier
    ) {
        composable(
            route = ScreenName.Categories.name,
        ) {
            CategoryScreen(
                onCategoryClicked = {
                    viewModel.updateCurrentCategory(it)
                    navController.navigate(ScreenName.Locations.name)
                },
                uiState = uiState,
                highlighted = false,
                modifier = Modifier.fillMaxHeight()
            )
        }
        composable(route = ScreenName.Locations.name) {
            LocationsScreen(
                category = uiState.currentCategory,
                onLocationClicked = {
                    viewModel.updateCurrentLocation(it)
                    navController.navigate(ScreenName.Detail.name)
                },
                uiState = uiState,
                highlighted = false,
                modifier = Modifier.fillMaxHeight()
            )
        }
        composable(route = ScreenName.Detail.name) {
            DetailsScreen(
                location = uiState.currentLocation
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NYEatsPreview() {
    NYEatsTheme {
        NYEats(windowSize = WindowWidthSizeClass.Compact)
    }
}

/*@Preview(showBackground = true, showSystemUi = true, device = Devices.TABLET)
@Composable
fun NYEatsExpandedPreview() {
    NYEatsTheme {
        NYEats(windowSize = WindowWidthSizeClass.Expanded)
    }
}*/

/*@Preview(showBackground = true, showSystemUi = true, device = Devices.TABLET)
@Composable
fun NYEatsCategoriesAndLocationsExpandedPreview() {
    NYEatsTheme {
        CategoriesAndLocationsScreen({}, {}, categories[0])
    }
}*/

/*@Preview(showBackground = true, showSystemUi = true, device = Devices.TABLET)
@Composable
fun NYEatsLocationsAndDetailsExpandedPreview() {
    NYEatsTheme {
        LocationsAndDetailsScreen({}, categories[0], coffeeShops[2])
    }
}*/

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    NYEatsTheme {
        DetailsScreen(location = coffeeShops[0])
    }
}*/

/*
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
*/

@Preview
@Composable
fun ListsPreview() {
    NYEatsTheme {
        LocationsList(list = coffeeShops, {}, modifier = Modifier.fillMaxSize(), uiState = NYEatsUiState(), highlighted = false)
    }
}