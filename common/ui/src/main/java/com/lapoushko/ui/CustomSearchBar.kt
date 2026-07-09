@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    text: String? = null,
    onClick: (String) -> Unit = {},
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    queryReturn: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf(text ?: "") }
    var isActive by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(query) {
        queryReturn(query)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
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
                        onClick(query)
                        isActive = false
                    },
                    expanded = isActive,
                    onExpandedChange = {
                        isActive = it
                    },
                    placeholder = {
                        Text(text = placeholder, style = Typography.bodyLarge)
                    },
                    leadingIcon = {
                        if (onBack != null){
                            CustomIconButton(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                onClick = onBack
                            )
                        } else{
                            CustomIconButton(
                                imageVector = Icons.Filled.Search
                            )
                        }
                    },
                    trailingIcon = {
                        if (isActive) {
                            CustomIconButton(
                                imageVector = Icons.Outlined.Close,
                                onClick = {
                                    query = ""
                                    isActive = false
                                    onClick(query)
                                }
                            )
                        }
                    },
                )
            },
            expanded = false,
            onExpandedChange = {},
        ) {}
    }
}

@Composable
private fun CustomIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun CustomSearchPreview() {
    CustomSearchBar(onClick = {})
}