package com.example.app.data.remote

import com.example.app.model.Usuario
import com.example.app.model.UsuarioDTO
import com.example.app.model.*
import retrofit2.http.*

interface ApiService {
    // Autenticación

    @GET("loginDP360")
    suspend fun loginDP360(
        @Query("email") email: String,
        @Query("password") password: String
    ): Boolean

    @GET("loginINFO")
    suspend fun  loginINFO(
        @Query("email") email: String,
        @Query("password") password: String
    ): UsuarioDTO

    @POST("add")
    suspend fun addUsuario(
        @Body usuario: Usuario,
        @Query("direccion") direccion: String,
        @Query("telefono") telefono: String
    ): Usuario


}
