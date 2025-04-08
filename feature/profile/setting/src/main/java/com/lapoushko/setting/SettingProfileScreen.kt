package com.lapoushko.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.ui.auth.AuthDialog
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun SettingProfileScreen(
    onBack: () -> Unit,
    viewModel: SettingProfileScreenViewModel = koinViewModel()
) {
    val state = viewModel.state

    if (state.isNeedToShowAuthDialog) {
        AuthDialog(
            onClose = {
                viewModel.updateIsNeedToShowAuthDialog(false)
                onBack()
            },
            signUp = {
                viewModel.signUpUser(it.email.text, it.firstPassword.text)
            },
            signIn = {
                viewModel.signInUser(it.email.text, it.password.text)
            },
            isCorrectLogin = state.isCorrectSignIn,
            isLoading = state.isLoading
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingProfileScreenPreview() {
    SettingProfileScreen(onBack = {})
}