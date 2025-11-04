package com.example.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.NetworkModule
import com.example.app.data.repository.UsuarioRepository
import com.example.app.model.UsuarioDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = UsuarioRepository(NetworkModule.api)

    private val _loginResult = MutableStateFlow<UsuarioDTO?>(null)
    val loginResult: StateFlow<UsuarioDTO?> = _loginResult

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.loginInfo(email, password)
                _loginResult.value = user
            } catch (e: Exception) {
                _error.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }
}
