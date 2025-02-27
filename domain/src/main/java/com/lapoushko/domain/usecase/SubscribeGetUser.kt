package com.lapoushko.domain.usecase

import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository

/**
 * @author Lapoushko
 */
interface SubscribeGetUser {
    suspend fun getUser(): User
}

class SubscribeGetUserImpl(
    private val repo: UserRepository
): SubscribeGetUser{
    override suspend fun getUser(): User {
        return repo.getUser()
    }
}