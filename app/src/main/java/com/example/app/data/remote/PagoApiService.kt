package com.example.app.data.remote

import com.example.app.model.PagoRequest
import com.example.app.model.PagoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PagoApiService {

    @POST("api/pagos")
    suspend fun realizarPago(
        @Body request: PagoRequest
    ): Response<PagoResponse>
}
