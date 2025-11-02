package com.example.app.data.repository

import com.example.app.data.remote.ApiService
import com.example.app.model.AuthResponse
import com.example.app.model.LoginRequest

class AuthRepository(private val api: ApiService) {
    suspend fun login(email: String, password: String): AuthResponse =
        api.login(LoginRequest(email, password))
}
