package com.lapoushko.data.repo

import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository

/**
 * @author Lapoushko
 */
class UserRepositoryImpl() : UserRepository {
    override suspend fun getUser(): User {
        return User(
            id = 0L,
            name = "Vadim",
            email = "a@a.com",
            password = "1234567Aa",
        )
    }
}