package com.example.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.NetworkModule
import com.example.app.data.repository.AuthRepository
import com.example.app.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estructura del estado UI para el login
data class AuthUiState(
    val loading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)

// ViewModel principal para autenticación
class AuthViewModel(app: Application) : AndroidViewModel(app) {

    // Repositorio para llamadas a la API
    private val repo = AuthRepository(NetworkModule.api)

    // Manejador de sesión (DataStore)
    private val session = SessionManager(app.applicationContext)

    // Estado de la pantalla
    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state

    /**
     * Inicia sesión con el correo y contraseña.
     * Si es exitoso, guarda el token en DataStore.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val res = repo.login(email, password)
                // Guardar token en DataStore
                session.saveToken(res.token)
                // Actualizar estado con el token
                _state.value = AuthUiState(loading = false, token = res.token)
            } catch (e: Exception) {
                _state.value = AuthUiState(
                    loading = false,
                    error = e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    /**
     * Devuelve un flujo con el token guardado (DataStore).
     * Se usa para detectar si el usuario ya tenía sesión iniciada.
     */
    fun observeToken() = session.getToken

    /**
     * Elimina el token guardado (cerrar sesión).
     */
    fun logout() {
        viewModelScope.launch {
            session.clearToken()
            _state.value = AuthUiState()
        }
    }
}
