@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun CustomSearchBar(
    placeholder: String = "Введите название экскурсии",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }

    val iconSearch: @Composable () -> Unit = {
        IconSearchBar(
            imageVector = Icons.Outlined.Search,
            onClick = {
                onClick()
                query = ""
            }
        )
    }

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = {
                    onClick()
                    isActive = false
                },
                expanded = isActive,
                onExpandedChange = {
                    isActive = false
                },
                placeholder = {
                    Text(text = placeholder, style = Typography.bodyLarge)
                },
                leadingIcon = {
                    if (!isActive) {
                        iconSearch.invoke()
                    }
                },
                trailingIcon = {
                    if (isActive) {
                        IconSearchBar(
                            imageVector = Icons.Outlined.Close,
                            onClick = {
                                query = ""
                            }
                        )
                    }
                },
            )
        },
        expanded = isActive,
        onExpandedChange = {
            isActive = true
        },
    ) {}
}

@Composable
private fun IconSearchBar(
    imageVector: ImageVector,
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = Modifier.clickable {
            onClick()
        },
        imageVector = imageVector,
        contentDescription = null
    )
}

@Preview
@Composable
fun CustomSearchPreview() {
    CustomSearchBar(onClick = {})
}