package br.com.sigo.consultoria.services

import br.com.sigo.consultoria.dtos.ConsultaNormaDTO
import br.com.sigo.consultoria.dtos.NormasDTO
import br.com.sigo.consultoria.dtos.PageDTO
import br.com.sigo.consultoria.internet.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NormaService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("api/norma/consultarNormas")
    fun consultarNormas(
        @Body consultaNormaDTO: ConsultaNormaDTO
    ): Call<Response<PageDTO<NormasDTO>>>
}