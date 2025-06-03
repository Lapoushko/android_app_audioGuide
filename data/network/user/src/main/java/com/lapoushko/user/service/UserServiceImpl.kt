package com.lapoushko.user.service

import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.lapoushko.domain.entity.User
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
    ): Flow<UserRepository.AuthResponse> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(UserRepository.AuthResponse.Success)
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
    ): Flow<UserRepository.AuthResponse> = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(UserRepository.AuthResponse.Success)
                close()
            }
            .addOnFailureListener {
                trySend(UserRepository.AuthResponse.Error(it.message ?: ""))
                close()
            }
        awaitClose()
    }

    override fun signOutUser() {
        firebaseAuth.signOut()
    }

    override fun deleteUser(email: String, password: String): Flow<UserRepository.AuthResponse> =
        callbackFlow {
            val credential = EmailAuthProvider.getCredential(email, password)
            firebaseAuth.currentUser?.reauthenticate(credential)
                ?.addOnSuccessListener {
                    firebaseAuth.currentUser?.delete()
                        ?.addOnSuccessListener {
                            trySend(UserRepository.AuthResponse.Success)
                            close()
                        }
                        ?.addOnFailureListener {
                            trySend(UserRepository.AuthResponse.Error(it.message ?: ""))
                            close()
                        }
                }
                ?.addOnFailureListener {
                    trySend(UserRepository.AuthResponse.Error(it.message ?: ""))
                    close()
                }
            awaitClose()
        }

    override fun getUser(): Flow<User?> = callbackFlow {
        firebaseAuth.currentUser?.let { curUser ->
            trySend(
                User(
                    email = curUser.email ?: "",
                    uuid = curUser.uid
                )
            )
            close()
        } ?: {
            trySend(null)
            close()
        }
        awaitClose()
    }
}