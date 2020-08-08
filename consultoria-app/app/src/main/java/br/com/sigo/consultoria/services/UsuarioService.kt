package br.com.sigo.consultoria.services

import br.com.sigo.consultoria.dtos.UsuarioDTO
import br.com.sigo.consultoria.internet.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface UsuarioService {

    @POST("api/usuario")
    fun atualizaUsuarioSistema(
        @Body usuarioDTO: UsuarioDTO, @HeaderMap headers: Map<String, String>
    ): Call<Response<UsuarioDTO>>

    @POST("api/usuario/consultor")
    fun atualizaConsultor(
        @Body usuarioDTO: UsuarioDTO, @HeaderMap headers: Map<String, String>
    ): Call<Response<UsuarioDTO>>

    @POST("api/usuario/admin")
    fun atualizaAdmin(
        @Body usuarioDTO: UsuarioDTO, @HeaderMap headers: Map<String, String>
    ): Call<Response<UsuarioDTO>>

}
