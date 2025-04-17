package com.lapoushko.domain.service

import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository.AuthResponse
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface UserService {
    fun signUpUser(
        email: String,
        password: String
    ): Flow<AuthResponse>

    fun signInUser(
        email: String,
        password: String
    ): Flow<AuthResponse>

    fun signOutUser()

    fun deleteUser(email: String, password: String): Flow<AuthResponse>

    fun getUser(): Flow<User?>
}