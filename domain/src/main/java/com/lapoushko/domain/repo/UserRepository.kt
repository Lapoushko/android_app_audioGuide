package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.User

/**
 * @author Lapoushko
 */
interface UserRepository {
    suspend fun getUser(): User
}

class UserRepositoryImpl(): UserRepository{
    override suspend fun getUser(): User {
        return User(
            id = 0L,
            name = "Vadim",
            email = "a@a.com",
            password = "1234567Aa",
        )
    }
}