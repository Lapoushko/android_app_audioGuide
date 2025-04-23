package com.lapoushko.favourite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.feature.auth.AuthHelperViewModel
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.SelectionScreen
import com.lapoushko.ui.auth.AuthDialog
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onSurfaceLight
import com.lapoushko.util.ConnectivityObserver
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun FavouriteScreen(
    onClickDetail: (ExcursionItem) -> Unit,
    viewModel: FavouriteScreenViewModel = koinViewModel(),
    authHelperViewModel: AuthHelperViewModel = koinViewModel()
) {
    val state = viewModel.state
    val excursions = state.excursions
    val stateHelper = authHelperViewModel.state

    val user = stateHelper.user
    when (state.internetStatus) {
        ConnectivityObserver.Status.AVAILABLE -> {
            if (stateHelper.isNeedToShowAuth) {
                AuthDialog(
                    onClose = { authHelperViewModel.updateIsNeedToShowAuth(false) },
                    signUp = {
                        authHelperViewModel.signUpUser(it.email.text, it.firstPassword.text)
                    },
                    signIn = {
                        authHelperViewModel.signInUser(it.email.text, it.password.text)
                    },
                    isCorrectLogin = stateHelper.isCorrectSignIn,
                    isLoading = stateHelper.isLoading
                )
            } else {
                LaunchedEffect(user) {
                    if (user != null) viewModel.loadExcursions(user.uuid)
                }
                SelectionScreen(
                    onClickSearch = { viewModel.searchByName(it) },
                    onClickDetail = onClickDetail,
                    textSearch = "",
                    excursions = if (stateHelper.user != null) excursions else emptyList(),
                    nameScreen = stringResource(R.string.favourite),
                )
            }
        }

        else -> {
            Text(
                stringResource(R.string.error_internet),
                style = Typography.titleLarge,
                color = onSurfaceLight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavouriteScreenPreview() {
    FavouriteScreen(
        onClickDetail = {}
    )
}