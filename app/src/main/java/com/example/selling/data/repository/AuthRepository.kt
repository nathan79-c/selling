package com.example.selling.data.repository

import com.example.selling.data.repository.abstract_interface.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth): AuthRepository {

    override suspend fun signUp(email: String, password: String): AuthResult<String>{
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return AuthResult.Error("Utilisateur introuvable après inscription")
            AuthResult.Success(userId)

        }catch (e: Exception){
            AuthResult.Error(e.message)

        }

    }

    override suspend fun signIn(email: String, password: String): AuthResult<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return AuthResult.Error("Utilisateur introuvable après connexion")
            AuthResult.Success(userId)
        }catch (e: Exception){
            AuthResult.Error(e.message)
        }

    }
    override fun signOut() {
        auth.signOut()
    }

    override suspend fun sendPasswordReset(email: String): AuthResult<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message, e)
        }
    }
    override fun currentUserId(): String? = auth.currentUser?.uid
    override fun isLoggedIn(): Boolean = auth.currentUser != null
}

sealed class AuthResult<out T> {
    data object Loading : AuthResult<Nothing>()
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String?, val cause: Throwable? = null) : AuthResult<Nothing>()
}

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false,
    val userId: String? = null
)