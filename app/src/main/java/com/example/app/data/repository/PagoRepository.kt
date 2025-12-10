package com.example.app.data.repository

import com.example.app.data.remote.PagoApi
import com.example.app.model.PaymentRequestDTO
import com.example.app.model.PreferenceResponseDTO
import javax.inject.Inject

class PagoRepository @Inject constructor(
    private val pagoApi: PagoApi
) {

    suspend fun crearPreferencia(request: PaymentRequestDTO): PreferenceResponseDTO {
        return pagoApi.crearPreference(request)
    }
}
