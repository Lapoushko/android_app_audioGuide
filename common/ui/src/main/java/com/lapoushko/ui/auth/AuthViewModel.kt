package com.lapoushko.ui.auth

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lapoushko.ui.model.Error
import com.lapoushko.ui.model.Input
import com.lapoushko.ui.model.ProfileErrors
import com.lapoushko.ui.model.SignInData
import com.lapoushko.ui.model.SignUpData
import com.lapoushko.ui.model.TypeInput
import com.lapoushko.ui.model.checkErrorInput

/**
 * @author Lapoushko
 */
class AuthViewModel : ViewModel() {
    private var _state = MutableAuthState()
    val state = _state as AuthState

    fun updateDialogState(dialogState: AuthState.DialogState) {
        _state.dialogState = dialogState
    }

    fun updateSignUpEmail(input: String) {
        val errorsByChecks = hashMapOf(
            ProfileErrors.EmailError.naming[0] to (input.length < 32 && Patterns.EMAIL_ADDRESS.matcher(
                input
            ).matches())
        )
        _state.signUpData = state.signUpData.copy(
            email = input.checkErrorInput(
                adding = { state.errors.add(it) },
                removing = { if (it in state.errors) state.errors.remove(it) },
                corrects = errorsByChecks
            )
        )
    }

    fun updateFirstPassword(input: String) {
        val errorsByChecks = hashMapOf(
            ProfileErrors.PasswordError.naming[0] to (
                    input.length in 8..31 &&
                            input != input.uppercase() &&
                            input != input.lowercase() &&
                            !input.all { it.isDigit() } &&
                            !input.any { "!@#$%^&*()_+-=[]{}|;':\",.<>?/`~".contains(it) }
                    )
        )
        _state.signUpData = state.signUpData.copy(
            firstPassword = input.checkErrorInput(
                adding = { state.errors.add(it) },
                removing = { if (it in state.errors) state.errors.remove(it) },
                corrects = errorsByChecks
            )
        )
    }

    fun updateSecondPassword(input: String) {
        val errorsByChecks = hashMapOf(
            ProfileErrors.PasswordError.naming[0] to (
                    input.length in 8..31 &&
                            input != input.uppercase() &&
                            input != input.lowercase() &&
                            !input.all { it.isDigit() } &&
                            !input.any { "!@#$%^&*()_+-=[]{}|;':\",.<>?/`~".contains(it) }
                    ),
            ProfileErrors.PasswordError.naming[1] to
                    (state.signUpData.firstPassword.text == input)
        )
        _state.signUpData = state.signUpData.copy(
            secondPassword = input.checkErrorInput(
                adding = { state.errors.add(it) },
                removing = { if (it in state.errors) state.errors.remove(it) },
                corrects = errorsByChecks
            )
        )
    }

    fun updateSignInInput(input: String, typeInput: TypeInput) {
        val emailErrors = hashMapOf(
            ProfileErrors.EmailError.naming[0] to (input.isNotEmpty())
        )
        val passwordErrors = hashMapOf(
            ProfileErrors.PasswordError.naming[0] to (input.isNotEmpty())
        )

        when (typeInput) {
            TypeInput.EMAIL -> _state.signInData =
                state.signInData.copy(
                    email = input.checkErrorInput(
                        adding = { state.errors.add(it) },
                        removing = { if (it in state.errors) state.errors.remove(it) },
                        corrects = emailErrors
                    )
                )

            TypeInput.PASSWORD -> _state.signInData =
                state.signInData.copy(
                    password = input.checkErrorInput(
                        adding = { state.errors.add(it) },
                        removing = { if (it in state.errors) state.errors.remove(it) },
                        corrects = passwordErrors
                    )
                )
        }
    }

    fun updateIsAgreeSignUp(value: Boolean){
        _state.signUpData = state.signUpData.copy(isAgree = value)
    }

    fun clearState(state: AuthState.DialogState) {
        when (state) {
            AuthState.DialogState.SIGN_UP -> _state.signUpData = SignUpData(
                email = Input(text = "", error = null),
                firstPassword = Input(text = "", error = null),
                secondPassword = Input(text = "", error = null),
                isAgree = false
            )

            AuthState.DialogState.SIGN_IN -> _state.signInData = SignInData(
                email = Input(text = "", error = null),
                password = Input(text = "", error = null),
            )
        }
        _state.errors.clear()
    }

    fun checkCanSignUp() {
        updateSignUpEmail(state.signUpData.email.text)
        updateFirstPassword(state.signUpData.firstPassword.text)
        updateSecondPassword(state.signUpData.secondPassword.text)
        _state.isCanSignUp = state.errors.isEmpty() && state.signUpData.isAgree
    }

    fun checkCanSignIn() {
        updateSignInInput(state.signInData.email.text, TypeInput.EMAIL)
        updateSignInInput(state.signInData.password.text, TypeInput.PASSWORD)
        _state.isCanSignIn = state.errors.isEmpty()
    }

    private class MutableAuthState : AuthState {
        override var dialogState: AuthState.DialogState by mutableStateOf(AuthState.DialogState.SIGN_UP)
        override var signUpData: SignUpData by mutableStateOf(
            SignUpData(
                email = Input(text = "", error = null),
                firstPassword = Input(text = "", error = null),
                secondPassword = Input(text = "", error = null),
                isAgree = false
            )
        )
        override var signInData: SignInData by mutableStateOf(
            SignInData(
                email = Input(text = "", error = null),
                password = Input(text = "", error = null),
            )
        )

        override val errors: MutableSet<Error> by mutableStateOf(mutableSetOf())
        override var isCanSignUp: Boolean by mutableStateOf(false)
        override var isCanSignIn: Boolean by mutableStateOf(false)
    }
}

interface AuthState {
    val dialogState: DialogState

    //sigh Up
    val signUpData: SignUpData

    //sign In
    val signInData: SignInData

    val errors: MutableSet<Error>

    val isCanSignUp: Boolean
    val isCanSignIn: Boolean

    enum class DialogState {
        SIGN_IN,
        SIGN_UP
    }
}