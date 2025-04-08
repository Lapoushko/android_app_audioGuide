@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.ui.CustomInputField
import com.lapoushko.ui.model.SignInData
import com.lapoushko.ui.model.SignUpData
import com.lapoushko.ui.model.TypeInput
import com.lapoushko.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun AuthDialog(
    viewModel: AuthViewModel = koinViewModel(),
    onClose: () -> Unit,
    signUp: (SignUpData) -> Unit,
    signIn: (SignInData) -> Unit,
    isCorrectLogin: Boolean,
    isLoading: Boolean
) {
    val state = viewModel.state
    val dialogState = state.dialogState

    when (dialogState) {
        AuthState.DialogState.SIGN_UP -> SignUpDialog(
            signUpData = state.signUpData,
            updateEmail = { viewModel.updateSignUpEmail(it) },
            updateFirstPassword = { viewModel.updateFirstPassword(it) },
            updateSecondPassword = { viewModel.updateSecondPassword(it) },
            onToSignIn = {
                viewModel.updateDialogState(AuthState.DialogState.SIGN_IN)
                viewModel.clearState(AuthState.DialogState.SIGN_UP)
            },
            signUp = {
                viewModel.checkCanSignUp()
                if (state.isCanSignUp) {
                    signUp(it)
                }
            },
            onClose = onClose,
        )

        AuthState.DialogState.SIGN_IN -> SignInDialog(
            signInData = state.signInData,
            updateEmail = { viewModel.updateSignInInput(it, TypeInput.EMAIL) },
            updatePassword = { viewModel.updateSignInInput(it, TypeInput.PASSWORD) },
            onToSignUp = {
                viewModel.updateDialogState(AuthState.DialogState.SIGN_UP)
                viewModel.clearState(AuthState.DialogState.SIGN_IN)
            },
            signIn = {
                viewModel.checkCanSignIn()
                if (state.isCanSignIn) {
                    signIn(it)
                }
            },
            onClose = onClose,
            isCorrectLogin = isCorrectLogin,
            isLoading = isLoading
        )
    }
}

@Composable
private fun SignUpDialog(
    signUpData: SignUpData,
    updateEmail: (String) -> Unit,
    updateFirstPassword: (String) -> Unit,
    updateSecondPassword: (String) -> Unit,
    signUp: (SignUpData) -> Unit,
    onClose: () -> Unit,
    onToSignIn: () -> Unit
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Регистрация", style = Typography.titleMedium) },
        confirmButton = {
            TextButton(onClick = {
                signUp(signUpData)
            }) {
                Text("Зарегистрироваться", style = Typography.labelMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Пропустить", style = Typography.labelMedium)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                signUpData.apply {
                    CustomInputField(
                        text = email.text,
                        label = "Ваша почта",
                        error = email.error?.code?.second ?: "",
                        placeholder = "Ваша почта",
                        onTextChange = updateEmail,
                        isError = !email.error?.code?.second.isNullOrEmpty(),
                    )
                    CustomInputField(
                        text = firstPassword.text,
                        label = "Пароль",
                        error = firstPassword.error?.code?.second ?: "",
                        placeholder = "Пароль",
                        isVisiblePassword = true,
                        keyboardType = KeyboardType.Password,
                        onTextChange = updateFirstPassword,
                        isError = !firstPassword.error?.code?.second.isNullOrEmpty(),
                    )
                    CustomInputField(
                        text = secondPassword.text,
                        label = "Повторите пароль",
                        error = secondPassword.error?.code?.second ?: "",
                        placeholder = "Повторите пароль",
                        isVisiblePassword = true,
                        keyboardType = KeyboardType.Password,
                        onTextChange = updateSecondPassword,
                        isError = !secondPassword.error?.code?.second.isNullOrEmpty(),
                    )
                    TextButton(onClick = onToSignIn, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Вход", style = Typography.bodyMedium, color = Color.Blue
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun SignInDialog(
    signInData: SignInData,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    signIn: (SignInData) -> Unit,
    onClose: () -> Unit,
    onToSignUp: () -> Unit,
    isCorrectLogin: Boolean,
    isLoading: Boolean
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Вход", style = Typography.titleMedium) },
        confirmButton = {
            TextButton(
                onClick = { signIn(signInData) },
                enabled = !isLoading
            ) {
                Text("Вход", style = Typography.labelMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Пропустить", style = Typography.labelMedium)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                if (!isCorrectLogin && !isLoading) {
                    Text(
                        text = "Неправильные данные",
                        style = Typography.bodyMedium,
                        color = Color.Red
                    )
                }

                signInData.apply {
                    CustomInputField(
                        text = email.text,
                        label = "Ваша почта",
                        error = email.error?.code?.second ?: "",
                        placeholder = "Ваша почта",
                        onTextChange = updateEmail,
                        isError = !email.error?.code?.second.isNullOrEmpty(),
                    )
                    CustomInputField(
                        text = password.text,
                        label = "Пароль",
                        error = password.error?.code?.second ?: "",
                        placeholder = "Пароль",
                        isVisiblePassword = true,
                        keyboardType = KeyboardType.Password,
                        onTextChange = updatePassword,
                        isError = !password.error?.code?.second.isNullOrEmpty(),
                    )
                    TextButton(onClick = onToSignUp, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Регистрация", style = Typography.bodyMedium, color = Color.Blue
                        )
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
private fun AuthDialogPreview() {
    AuthDialog(onClose = {}, signUp = {}, signIn = {}, isCorrectLogin = false, isLoading = false
    )
}