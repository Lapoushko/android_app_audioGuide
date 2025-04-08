package com.lapoushko.domain.service

import com.lapoushko.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface UserService {
    fun signUpUser(
        email: String,
        password: String
    ): Flow<UserRepository.AuthResponse>

    fun signInUser(
        email: String,
        password: String
    ): Flow<UserRepository.AuthResponse>
}