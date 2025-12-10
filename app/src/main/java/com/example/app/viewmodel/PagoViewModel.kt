package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PagoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PagoUiState())
    val uiState: StateFlow<PagoUiState> = _uiState

    // Ejemplo simple de cómo setear la URL (en tu caso llamarás repository)
    fun pagar(monto: Double, descripcion: String, usuarioId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Llamar a repository para crear preferencia y obtener initPoint
                // val response = repository.crearPreferencia(...)
                // val initPoint = response.initPoint

                // Simulamos resultado:
                val initPoint = "https://www.mercadopago.cl/checkout/v1/redirect?pref_id=TEST123"

                _uiState.value = PagoUiState(
                    isLoading = false,
                    exito = true,
                    urlWebPay = initPoint,
                    mensaje = "Preferencia creada"
                )
            } catch (e: Exception) {
                _uiState.value = PagoUiState(
                    isLoading = false,
                    exito = false,
                    mensaje = "Error: ${e.message}"
                )
            }
        }
    }
}
