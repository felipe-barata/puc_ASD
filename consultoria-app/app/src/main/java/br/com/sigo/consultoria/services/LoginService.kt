package br.com.sigo.consultoria.services

import br.com.sigo.consultoria.dtos.JwtAuthenticationDto
import br.com.sigo.consultoria.dtos.TokenDto
import br.com.sigo.consultoria.internet.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("auth")
    fun login(@Body json: JwtAuthenticationDto): Call<Response<TokenDto>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("auth/refresh")
    fun gerarRefreshTokenJwt(@HeaderMap headers: Map<String, String>): Call<Response<TokenDto>>
}