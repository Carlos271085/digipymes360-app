package com.example.app.data.repository

import com.example.app.data.remote.PagoApiService
import com.example.app.model.PagoRequest
import com.example.app.model.PagoResponse
import javax.inject.Inject

class PagoRepository @Inject constructor(
    private val api: PagoApiService
) {

    /**
     * Envía un pago al backend y retorna un Result<PagoResponse>
     * para que el ViewModel pueda interpretar éxito o error.
     */
    suspend fun realizarPago(request: PagoRequest): Result<PagoResponse> {
        return try {
            // Llamada HTTP al backend
            val response = api.realizarPago(request)

            // Si responde OK (200) y trae body
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                // Si el backend responde error (400, 500, etc)
                Result.failure(
                    Exception("Error en el pago: ${response.code()} - ${response.message()}")
                )
            }
        } catch (e: Exception) {
            // Error de red, servidor caído, timeout, etc.
            Result.failure(e)
        }
    }
}
