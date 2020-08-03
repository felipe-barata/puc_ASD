package br.com.sigo.consultoria.dtos

import br.com.sigo.consultoria.enums.EnumAtributosPesquisaEmpresa
import java.io.Serializable

data class ConsultarEmpresaDTO(var atributo: EnumAtributosPesquisaEmpresa?, var valor: String) :
    Serializable