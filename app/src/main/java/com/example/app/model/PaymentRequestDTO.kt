package com.example.app.model
import com.google.gson.annotations.SerializedName

data class PaymentRequestDTO(
    @SerializedName("items") val items: List<PaymentItemDTO>
)