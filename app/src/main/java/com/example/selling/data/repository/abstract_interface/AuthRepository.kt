package com.example.selling.data.repository.abstract_interface

import com.example.selling.data.repository.AuthResult

interface AuthRepository {
    suspend fun signUp(email: String, password: String): AuthResult<String> // userId
    suspend fun signIn(email: String, password: String): AuthResult<String> // userId
    suspend fun sendPasswordReset(email: String): AuthResult<Unit>
    fun signOut()
    fun currentUserId(): String?
    fun isLoggedIn(): Boolean = currentUserId() != null
}