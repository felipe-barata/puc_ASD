package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class IntegracaoDTO(var idSistema: Int, var idIntegracao: Long, var descricao: String) :
    Serializable