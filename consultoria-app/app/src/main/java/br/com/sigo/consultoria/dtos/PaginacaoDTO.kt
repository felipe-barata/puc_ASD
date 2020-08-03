package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class PaginacaoDTO(var pagina: Int, var qtdRegistros: Int) : Serializable