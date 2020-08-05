package br.com.sigo.consultoria.services

import br.com.sigo.consultoria.dtos.CategoriaDTO
import br.com.sigo.consultoria.dtos.PageDTO
import br.com.sigo.consultoria.dtos.PaginacaoDTO
import br.com.sigo.consultoria.internet.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface CategoriaService {

    @POST("api/categoria")
    fun atualizarCategoria(
        @Body categoriaDTO: CategoriaDTO, @HeaderMap headers: Map<String, String>
    ): Call<Response<CategoriaDTO>>

    @POST("api/categoria/listar")
    fun retornaTodasCategorias(
        @Body paginacaoDTO: PaginacaoDTO, @HeaderMap headers: Map<String, String>
    ): Call<Response<PageDTO<CategoriaDTO>>>

}