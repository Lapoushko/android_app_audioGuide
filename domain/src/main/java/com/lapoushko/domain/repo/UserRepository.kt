package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.User

/**
 * @author Lapoushko
 */
interface UserRepository {
    suspend fun getUser(): User
}