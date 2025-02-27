package com.lapoushko.setting

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lapoushko.ui.model.Error
import com.lapoushko.ui.model.Input
import com.lapoushko.ui.model.ProfileErrors
import com.lapoushko.ui.model.checkErrorInput

/**
 * @author Lapoushko
 */
class SettingProfileScreenViewModel : ViewModel() {
    private var _state = MutableSettingProfileScreenState()
    val state = _state as SettingProfileScreenState

    private val errors: MutableSet<Error> = mutableSetOf()

    fun updateName(input: String) {
        val errorsByChecks = hashMapOf(
            ProfileErrors.NameError.naming[0] to (input.length < 32 && Regex("^[a-zA-Z0-9-]+\$").matches(
                input
            )),
        )
        _state.name = input.checkErrorInput(
            adding = { errors.add(it) },
            removing = { if (it in errors) errors.remove(it) },
            corrects = errorsByChecks
        )
    }

    fun updateEmail(input: String) {
        val errorsByChecks = hashMapOf(
            ProfileErrors.EmailError.naming[0] to (input.length < 32 && Patterns.EMAIL_ADDRESS.matcher(
                input
            ).matches())
        )
        _state.email = input.checkErrorInput(
            adding = { errors.add(it) },
            removing = { if (it in errors) errors.remove(it) },
            corrects = errorsByChecks
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
        _state.firstPassword = input.checkErrorInput(
            adding = { errors.add(it) },
            removing = { if (it in errors) errors.remove(it) },
            corrects = errorsByChecks
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
                    (state.firstPassword.text == input)
        )
        _state.secondPassword = input.checkErrorInput(
            adding = { errors.add(it) },
            removing = { if (it in errors) errors.remove(it) },
            corrects = errorsByChecks
        )
    }

    fun saveSettings(onBack: () -> Unit){
        updateName(state.name.text)
        updateEmail(state.email.text)
        updateFirstPassword(state.firstPassword.text)
        updateSecondPassword(state.secondPassword.text)
        if (errors.isEmpty()){
            onBack()
        }
    }

    private class MutableSettingProfileScreenState : SettingProfileScreenState {
        override var name: Input by mutableStateOf(Input(text = "", error = null))
        override var email: Input by mutableStateOf(Input(text = "", error = null))
        override var firstPassword: Input by mutableStateOf(Input(text = "", error = null))
        override var secondPassword: Input by mutableStateOf(Input(text = "", error = null))
    }
}