package com.lapoushko.ui.model

/**
 * @author Lapoushko
 */
data class SignUpData(
    val email: Input,
    val firstPassword: Input,
    val secondPassword: Input,
    val isAgree: Boolean
)

data class SignInData(
    val email: Input,
    val password: Input
)