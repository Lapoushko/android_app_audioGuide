package com.lapoushko.user.service

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.lapoushko.domain.repo.UserRepository
import com.lapoushko.domain.service.UserService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @author Lapoushko
 */
class UserServiceImpl : UserService {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    override fun signUpUser(
        email: String,
        password: String
    ): Flow<UserRepository.AuthResponse> = callbackFlow{
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) trySend(UserRepository.AuthResponse.Success)
                close()
            }
            .addOnFailureListener {
                trySend(UserRepository.AuthResponse.Error(it.message ?: ""))
                close()
            }
        awaitClose()
    }

    override fun signInUser(
        email: String,
        password: String
    ): Flow<UserRepository.AuthResponse> = callbackFlow{
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) trySend(UserRepository.AuthResponse.Success)
                close()
            }
            .addOnFailureListener {
                trySend(UserRepository.AuthResponse.Error(it.message ?: ""))
                close()
            }
        awaitClose()
    }
}