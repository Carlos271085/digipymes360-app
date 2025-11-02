package com.example.app.data.remote

import com.example.app.model.*
import retrofit2.http.*

interface ApiService {
    // Autenticación
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    // Usuario autenticado
    @GET("users/me")
    suspend fun me(@Header("Authorization") bearer: String): UserProfile

    // Billetera
    @GET("wallet/balance")
    suspend fun getBalance(@Header("Authorization") bearer: String): BalanceResponse

    // Enviar dinero
    @POST("payments/send")
    suspend fun sendPayment(
        @Header("Authorization") bearer: String,
        @Body request: PaymentRequest
    ): PaymentResponse

    // Transacciones
    @GET("transactions")
    suspend fun getTransactions(
        @Header("Authorization") bearer: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): PagedTransactions
}
