package com.example.app.model

import com.google.gson.annotations.SerializedName

data class PreferenceResponseDTO(
    @SerializedName("initPoint") val initPoint: String
)