package com.lapoushko.ui.model

/**
 * @author Lapoushko
 */

data class Input(
    val text: String,
    val error: Error?
)

enum class TypeInput{
    EMAIL,
    PASSWORD
}

fun String.checkErrorInput(
    adding: ((Error) -> Unit)?,
    removing: ((Error) -> Unit)?,
    corrects: Map<Error, Boolean>
): Input {
    if (adding != null && removing != null) {
        corrects.forEach { correct ->
            if (correct.value) {
                removing(correct.key)
            } else {
                adding(correct.key)
                return Input(text = this, error = correct.key)
            }
        }
    }
    return Input(text = this, error = null)
}

interface CustomErrors {
    val naming: List<Error>
}

class Error(
    val code: Pair<Int, String>
)

sealed class ProfileErrors(override val naming: List<Error>) : CustomErrors {
    data object EmailError : ProfileErrors(
        listOf("Неправильая почта", "Такая почта уже существует").mapIndexed { index, s ->
            Error(
                Pair(index, s)
            )
        }
    )

    data object PasswordError : ProfileErrors(
        listOf("Неправильный пароль", "Пароль не повторяется").mapIndexed { index, s ->
            Error(
                Pair(
                    index,
                    s
                )
            )
        }
    )
}