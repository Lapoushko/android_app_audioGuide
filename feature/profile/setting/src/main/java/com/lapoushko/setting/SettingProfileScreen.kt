package com.lapoushko.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.feature.auth.AuthHelperViewModel
import com.lapoushko.ui.auth.AuthDialog
import com.lapoushko.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingProfileScreen(
    onBack: () -> Unit,
    viewModel: SettingProfileScreenViewModel = koinViewModel(),
    authHelperViewModel: AuthHelperViewModel = koinViewModel()
) {
    val state = viewModel.state

    when (state.dialogState) {
        SettingProfileScreenState.DialogState.EMPTY -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.profile),
                            style = Typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    TextButton(
                        onClick = {
                            viewModel.updateIsNeedToShowAuthDialog(SettingProfileScreenState.DialogState.SING_OUT)
                        }
                    ) {
                        Text(text = stringResource(R.string.sign_out), style = Typography.bodyLarge)
                    }
//                    TextButton(
//                        onClick = {
//                            viewModel.updateIsNeedToShowAuthDialog(SettingProfileScreenState.DialogState.DELETE)
//                        }
//                    ) {
//                        Text(
//                            text = stringResource(R.string.delete_profile),
//                            style = Typography.bodyLarge
//                        )
//                    }
                }
            }
        }

        SettingProfileScreenState.DialogState.AUTH -> {
            AuthDialog(
                onClose = {
                    authHelperViewModel.updateIsNeedToShowAuth(false)
                    onBack()
                },
                signUp = {
                    authHelperViewModel.signUpUser(it.email.text, it.firstPassword.text)
                },
                signIn = {
                    authHelperViewModel.signInUser(it.email.text, it.password.text)
                },
                isCorrectLogin = state.isCorrectSignIn,
                isLoading = state.isLoading
            )
        }

        SettingProfileScreenState.DialogState.SING_OUT -> {
            QuestionAlertDialog(
                title = stringResource(R.string.question_sign_out),
                textConfirm = stringResource(R.string.quit),
                onConfirm = {
                    viewModel.signOutUser()
                    viewModel.updateIsNeedToShowAuthDialog(SettingProfileScreenState.DialogState.EMPTY)
                    onBack()
                },
                onCancel = { viewModel.updateIsNeedToShowAuthDialog(SettingProfileScreenState.DialogState.EMPTY) }
            )
        }

        SettingProfileScreenState.DialogState.DELETE -> {
            QuestionAlertDialog(
                title = stringResource(R.string.question_delete_profile),
                textConfirm = stringResource(R.string.delete_profile),
                onConfirm = {
                    viewModel.signOutUser()
                    viewModel.updateIsNeedToShowAuthDialog(SettingProfileScreenState.DialogState.EMPTY)
                    onBack()
                },
                onCancel = { viewModel.updateIsNeedToShowAuthDialog(SettingProfileScreenState.DialogState.EMPTY) }
            )
        }
    }
}

@Composable
private fun QuestionAlertDialog(
    title: String,
    textConfirm: String,
    textClose: String = stringResource(R.string.cancel),
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = title, style = Typography.titleMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                },
            ) {
                Text(text = textConfirm, style = Typography.labelMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onCancel()
            }) {
                Text(text = textClose, style = Typography.labelMedium)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingProfileScreenPreview() {
    SettingProfileScreen(onBack = {})
}