package br.com.sigo.consultoria.services

import br.com.sigo.consultoria.dtos.EmpresaDTO
import br.com.sigo.consultoria.internet.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface EmpresaService {

    @POST("api/empresa")
    fun atualizaEmpresa(
        @Body empresaDTO: EmpresaDTO, @HeaderMap headers: Map<String, String>
    ): Call<Response<EmpresaDTO>>

}