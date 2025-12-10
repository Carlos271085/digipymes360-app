package com.example.app.data.remote

import com.example.app.model.PaymentRequestDTO
import com.example.app.model.PreferenceResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface PagoApi {

    @POST("api/pago/preference")
    suspend fun crearPreference(
        @Body request: PaymentRequestDTO
    ): PreferenceResponseDTO
}
