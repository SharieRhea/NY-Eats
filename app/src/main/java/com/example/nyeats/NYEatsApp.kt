package com.example.nyeats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyeats.model.CategoriesRepository.categories
import com.example.nyeats.model.Category
import com.example.nyeats.model.Location
import com.example.nyeats.model.LocationsRepository.coffeeShops
import com.example.nyeats.ui.theme.NYEatsTheme

class NYEatsApp {

    @Composable
    fun NYEatsApp(modifier: Modifier) {
        // the main thing
    }

    @Composable
    fun CategoryItem(
        category: Category,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.category_card_inner_padding))
            ) {
                Icon(
                    painter = painterResource(id = category.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.category_card_inner_padding)))
                Text(
                    text = stringResource(id = category.name),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

    @Composable
    fun CategoriesList(
        list: List<Category>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing))
        ) {
            items(list) {
                CategoryItem(category = it, modifier = Modifier
                    .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun LocationsList(
        list: List<Location>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing))
        ) {
            items(list) {
                LocationItem(location = it, modifier = Modifier
                    .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun LocationItem(
        location: Location,
        modifier: Modifier = Modifier
    ) {
        Card(
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
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    @Preview
    @Composable
    fun CategoryItemPreview() {
        NYEatsTheme {
            CategoryItem(category = categories[0])
        }
    }

    @Preview
    @Composable
    fun LocationItemPreview() {
        NYEatsTheme {
            LocationItem(location = coffeeShops[0])
        }
    }

    @Preview
    @Composable
    fun ListsPreview() {
        NYEatsTheme {
            LocationsList(list = coffeeShops)
        }
    }
}