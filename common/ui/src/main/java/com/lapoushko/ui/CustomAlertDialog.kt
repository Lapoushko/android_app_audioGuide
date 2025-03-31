package com.lapoushko.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun CustomAlertDialog(
    title: String = "Подтверждение",
    text: String,
    textAgree: String = "Да",
    textDisagree: String = "Нет",
    onAgree: () -> Unit,
    onDisagree: () -> Unit
){
    AlertDialog(
        title = {
            Text(text = title, style = Typography.titleMedium)
        },
        text = {
            Text(text = text, style = Typography.bodyMedium)
        },
        onDismissRequest = onDisagree,
        confirmButton = {
            TextButton(onClick = onAgree) {
                Text(text = textAgree, style = Typography.bodyMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = onDisagree) {
                Text(text = textDisagree, style = Typography.bodyMedium)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomAlertDialogPreview(){
    CustomAlertDialog(
        text = "Вы хотите скачать экскурсию?",
        textAgree = "Да",
        textDisagree = "Нет",
        onAgree = {},
        onDisagree = {}
    )
}
