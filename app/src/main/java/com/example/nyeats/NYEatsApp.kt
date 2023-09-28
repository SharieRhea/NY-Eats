package com.example.nyeats

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyeats.model.CategoriesRepository.categories
import com.example.nyeats.model.Category
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.category_card_inner_padding))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_coffee),
                    contentDescription = "",
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.category_card_inner_padding)))
                Text(
                    text = stringResource(id = category.name),
                    style = MaterialTheme.typography.titleLarge
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
}