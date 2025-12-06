package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.repository.PagoRepository
import com.example.app.model.PagoRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PagoUiState(
    val isLoading: Boolean = false,
    val exito: Boolean? = null,
    val mensaje: String? = null,
    val codigoTransaccion: String? = null
)

@HiltViewModel
class PagoViewModel @Inject constructor(
    private val repository: PagoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PagoUiState())
    val uiState: StateFlow<PagoUiState> = _uiState

    fun pagar(monto: Double, descripcion: String, usuarioId: Long) {
        viewModelScope.launch {

            _uiState.value = PagoUiState(isLoading = true)

            val request = PagoRequest(monto, descripcion, usuarioId)
            val result = repository.realizarPago(request)

            result.onSuccess { res ->
                _uiState.value = PagoUiState(
                    isLoading = false,
                    exito = res.status == "APROBADO",
                    mensaje = res.mensaje,
                    codigoTransaccion = res.codigoTransaccion
                )
            }

            result.onFailure { err ->
                _uiState.value = PagoUiState(
                    isLoading = false,
                    exito = false,
                    mensaje = err.message
                )
            }
        }
    }
}
