package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface UserRepository {
    fun signUpUser(email: String, password: String): Flow<AuthResponse>

    fun signInUser(email: String, password: String): Flow<AuthResponse>

    fun signOutUser()

    fun deleteUser(email: String, password: String): Flow<AuthResponse>

    fun getUser(): Flow<User?>

    sealed class AuthResponse{
        data object Success: AuthResponse()
        data class Error(val error: String): AuthResponse()
    }
}