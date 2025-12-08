package com.example.app.data.remote

import com.example.app.model.Usuario
import com.example.app.model.UsuarioDTO
import com.example.app.model.*
import retrofit2.http.*

interface ApiService {
    @GET("api/v2/user/loginDP360")
    suspend fun loginDP360(
        @Query("email") email: String,
        @Query("password") password: String
    ): Boolean

    @GET("api/v2/user/loginINFO")
    suspend fun loginINFO(
        @Query("email") email: String,
        @Query("password") password: String
    ): UsuarioDTO

    @POST("api/v2/user/add")
    suspend fun addUsuario(
        @Body usuario: UsuarioRegistro,
        @Query("direccion") direccion: String,
        @Query("telefono") telefono: String
    ): Usuario

}
