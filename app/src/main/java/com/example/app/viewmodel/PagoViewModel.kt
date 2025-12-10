package com.example.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.PaymentItemDTO
import com.example.app.model.PaymentRequestDTO
import com.example.app.data.repository.PagoRepository
import com.example.app.ui.state.PagoUiState
import com.example.app.BuildConfig
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

    // Public Key desde BuildConfig (gradle)
    private val publicKey: String = BuildConfig.MP_PUBLIC_KEY

    fun onWebViewNavigation(url: String?) {
        if (url == null) return

        when {
            url.contains("success", ignoreCase = true) -> {
                _uiState.value = _uiState.value.copy(
                    pagoCompletado = true
                )
            }

            url.contains("failure", ignoreCase = true) -> {
                _uiState.value = _uiState.value.copy(
                    pagoFallido = true
                )
            }

            url.contains("pending", ignoreCase = true) -> {
                _uiState.value = _uiState.value.copy(
                    pagoPendiente = true
                )
            }
        }
    }

    fun pagar(monto: Double, descripcion: String, usuarioId: Long) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                mensaje = null,
                publicKey = publicKey
            )

            try {
                val item = PaymentItemDTO(
                    title = descripcion,
                    quantity = 1,
                    unitPrice = BigDecimal(monto)
                )

                val request = PaymentRequestDTO(
                    items = listOf(item),
                    usuarioId = usuarioId
                )

                val response = pagoRepository.crearPreferencia(request)

                // 👉 PRIORIDAD AL SANDBOX
                val url = response.sandbox_init_point
                    ?: response.init_point
                    ?: throw Exception("Backend no devolvió ninguna URL válida (init_point ni sandbox_init_point)")

                Log.d("MercadoPago", "URL seleccionada: $url")

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    exito = true,
                    urlWebPay = url,
                    mensaje = "Preferencia creada correctamente",
                    publicKey = publicKey
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    exito = false,
                    mensaje = "Error al generar pago: ${e.localizedMessage}",
                    publicKey = publicKey
                )
            }
        }
    }
}
