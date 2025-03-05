package com.lapoushko.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun CustomInputField(
    text: String,
    label: String,
    placeholder: String,
    error: String = "",
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    isVisiblePassword: Boolean? = null
) {
    var isVisiblePass by remember { mutableStateOf(isVisiblePassword) }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = label, style = Typography.bodySmall) },
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                text = placeholder, fontSize = 15.sp
            )
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                )
            }
        },
        maxLines = 1,
        visualTransformation = if (isVisiblePass == true) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            isVisiblePass?.let {
                IconButton(onClick = { isVisiblePass = !isVisiblePass!! }) {
                    val image =
                        if (isVisiblePass!!) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    Icon(
                        imageVector = image, contentDescription = null
                    )
                }
            }
        },
        shape = RoundedCornerShape(4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomInputPreview() {
    CustomInputField(
        text = "Текст",
        onTextChange = {},
        label = "Поле",
        isError = true,
        error = "Код ошибки",
        isVisiblePassword = true,
        placeholder = "Текст",
    )
}