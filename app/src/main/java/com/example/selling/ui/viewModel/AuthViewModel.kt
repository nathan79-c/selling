package com.example.selling.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selling.data.repository.AuthRepository
import com.example.selling.data.repository.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        AuthUiState(
            isAuthenticated = repo.isLoggedIn(),
            userId = repo.currentUserId()
        )
    )
    val state: StateFlow<AuthUiState> = _state

    fun onEmailChange(value: String) = _state.update { it.copy(email = value, errorMessage = null) }
    fun onPasswordChange(value: String) = _state.update { it.copy(password = value, errorMessage = null) }
    fun onConfirmPasswordChange(value: String) = _state.update { it.copy(confirmPassword = value, errorMessage = null) }

    fun checkSession() {
        _state.update { it.copy(isAuthenticated = repo.isLoggedIn(), userId = repo.currentUserId()) }
    }

    fun signIn() = viewModelScope.launch {
        val email = state.value.email.trim()
        val password = state.value.password

        if (email.isEmpty() || password.isEmpty()) {
            _state.update { it.copy(errorMessage = "Email et mot de passe requis") }
            return@launch
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }
        when (val res = repo.signIn(email, password)) {
            is AuthResult.Success -> _state.update { it.copy(isLoading = false, isAuthenticated = true, userId = res.data) }
            is AuthResult.Error -> _state.update { it.copy(isLoading = false, errorMessage = res.message ?: "Erreur de connexion") }
            AuthResult.Loading -> Unit
        }
    }

    fun signUp() = viewModelScope.launch {
        val email = state.value.email.trim()
        val pwd = state.value.password
        val confirm = state.value.confirmPassword

        if (email.isEmpty() || pwd.isEmpty() || confirm.isEmpty()) {
            _state.update { it.copy(errorMessage = "Tous les champs sont requis") }
            return@launch
        }
        if (pwd != confirm) {
            _state.update { it.copy(errorMessage = "Les mots de passe ne correspondent pas") }
            return@launch
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }
        when (val res = repo.signUp(email, pwd)) {
            is AuthResult.Success -> _state.update { it.copy(isLoading = false, isAuthenticated = true, userId = res.data) }
            is AuthResult.Error -> _state.update { it.copy(isLoading = false, errorMessage = res.message ?: "Échec de l'inscription") }
            AuthResult.Loading -> Unit
        }
    }

    fun sendReset() = viewModelScope.launch {
        val email = state.value.email.trim()
        if (email.isEmpty()) {
            _state.update { it.copy(errorMessage = "Saisis ton email pour réinitialiser le mot de passe") }
            return@launch
        }
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        when (val res = repo.sendPasswordReset(email)) {
            is AuthResult.Success -> _state.update { it.copy(isLoading = false, errorMessage = "Email de réinitialisation envoyé ✅") }
            is AuthResult.Error -> _state.update { it.copy(isLoading = false, errorMessage = res.message ?: "Échec de l'envoi") }
            AuthResult.Loading -> Unit
        }
    }

    fun signOut() {
        repo.signOut()
        _state.update { AuthUiState() } // reset complet
    }
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