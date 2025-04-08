package com.lapoushko.data.repo

import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository
import com.lapoushko.domain.service.UserService
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override fun signUpUser(
        email: String,
        password: String
    ): Flow<UserRepository.AuthResponse> {
        return userService.signUpUser(email, password)
    }

    override fun signInUser(
        email: String,
        password: String
    ): Flow<UserRepository.AuthResponse> {
        return userService.signInUser(email, password)
    }

    override suspend fun getUser(): User {
        return User(
            email = "",
            password = "",
            uuid = ""
        )
    }
}