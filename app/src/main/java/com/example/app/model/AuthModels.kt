package com.example.app.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val user: UserProfile
)

data class UserProfile(
    val id: String,
    val name: String,
    val email: String
)
