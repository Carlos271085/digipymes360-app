package com.example.app.model

import com.google.gson.annotations.SerializedName

data class PreferenceResponseDTO(
    val id: String,
    val init_point: String?,
    val sandbox_init_point: String?
)