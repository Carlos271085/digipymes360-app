package com.example.app.data.repository

import com.example.app.data.remote.ApiService
import com.example.app.model.*

class WalletRepository(private val api: ApiService) {
    suspend fun balance(token: String) = api.getBalance("Bearer $token")
    suspend fun transactions(token: String) = api.getTransactions("Bearer $token")
    suspend fun send(token: String, to: String, amount: Double, concept: String?) =
        api.sendPayment("Bearer $token", PaymentRequest(to, amount, concept = concept))
}
