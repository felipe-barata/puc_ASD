package br.com.sigo.consultoria.dtos

import br.com.sigo.consultoria.enums.EnumAtributosPesquisaUsuario
import java.io.Serializable

data class ConsultarUsuarioDTO(var atributo: EnumAtributosPesquisaUsuario?, var valor: String) :
    Serializable