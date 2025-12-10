package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.PaymentItemDTO
import com.example.app.model.PaymentRequestDTO
import com.example.app.data.repository.PagoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PagoViewModel @Inject constructor(
    private val pagoRepository: PagoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PagoUiState())
    val uiState: StateFlow<PagoUiState> = _uiState

    fun pagar(monto: Double, descripcion: String, usuarioId: Long) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true, mensaje = null)

            try {
                // Crear item esperado por backend
                val item = PaymentItemDTO(
                    title = descripcion,
                    quantity = 1,
                    unitPrice = BigDecimal(monto)
                )

                // crear request completo
                val request = PaymentRequestDTO(
                    items = listOf(item),
                    usuarioId = usuarioId
                )

                // llamar a backend
                val response = pagoRepository.crearPreferencia(request)

                // usar init_point o sandbox_init_point
                val url = response.init_point ?: response.sandbox_init_point

                if (url == null) {
                    throw Exception("El backend no devolvió ninguna URL válida (init_point ni sandbox_init_point)")
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    exito = true,
                    urlWebPay = url,
                    mensaje = "Preferencia creada con éxito"
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    exito = false,
                    mensaje = "Error al generar pago: ${e.localizedMessage}"
                )
            }
        }
    }
}
