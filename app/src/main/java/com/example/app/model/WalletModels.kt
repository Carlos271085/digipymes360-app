package com.example.app.model

data class BalanceResponse(
    val balance: Double,
    val currency: String
)

data class PaymentRequest(
    val to: String,
    val amount: Double,
    val currency: String = "CLP",
    val concept: String? = null
)

data class PaymentResponse(
    val id: String,
    val status: String,
    val timestamp: String
)

data class Transaction(
    val id: String,
    val type: String,
    val amount: Double,
    val currency: String,
    val concept: String?,
    val createdAt: String
)

data class PagedTransactions(
    val items: List<Transaction>,
    val page: Int,
    val size: Int,
    val total: Int
)
