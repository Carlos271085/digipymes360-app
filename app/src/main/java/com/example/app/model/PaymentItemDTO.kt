package com.example.app.model
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PaymentItemDTO(
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("unitPrice") val unitPrice: BigDecimal
)