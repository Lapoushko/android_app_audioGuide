package com.lapoushko.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.ui.CustomInputField
import com.lapoushko.ui.CustomOutlinedButton
import com.lapoushko.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingProfileScreen(
    onBack: () -> Unit,
    viewModel: SettingProfileScreenViewModel = koinViewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Настройки профиля",
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
        ) {
            state.name.apply {
                CustomInputField(
                    text = this.text,
                    label = "Имя",
                    error = this.error?.code?.second ?: "",
                    placeholder = "Имя",
                    onTextChange = { viewModel.updateName(it) },
                    isError = !this.error?.code?.second.isNullOrEmpty(),
                )
            }

            state.email.apply {
                CustomInputField(
                    text = this.text,
                    label = "Почта",
                    error = this.error?.code?.second ?: "",
                    placeholder = "Почта",
                    onTextChange = { viewModel.updateEmail(it) },
                    isError = !this.error?.code?.second.isNullOrEmpty(),
                )
            }

            state.firstPassword.apply {
                CustomInputField(
                    text = this.text,
                    label = "Пароль",
                    error = this.error?.code?.second ?: "",
                    placeholder = "Пароль",
                    isVisiblePassword = true,
                    keyboardType = KeyboardType.Password,
                    onTextChange = { viewModel.updateFirstPassword(it) },
                    isError = !this.error?.code?.second.isNullOrEmpty(),
                )
            }

            state.secondPassword.apply {
                CustomInputField(
                    text = this.text,
                    label = "Повторите пароль",
                    error = this.error?.code?.second ?: "",
                    placeholder = "Повторите пароль",
                    isVisiblePassword = true,
                    keyboardType = KeyboardType.Password,
                    onTextChange = { viewModel.updateSecondPassword(it) },
                    isError = !this.error?.code?.second.isNullOrEmpty(),
                )
            }
        }
        CustomOutlinedButton(
            text = "Сохранить",
            imageVector = Icons.Filled.Save,
            onClick = {
                viewModel.saveSettings(onBack)
            },
            isActive = false,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingProfileScreenPreview() {
    SettingProfileScreen(onBack = {})
}