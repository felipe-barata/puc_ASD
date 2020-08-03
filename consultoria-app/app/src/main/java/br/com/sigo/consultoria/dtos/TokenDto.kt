package br.com.sigo.consultoria.dtos

import br.com.sigo.consultoria.enums.PerfilEnum

data class TokenDto(
    var token: String,
    var username: String,
    var displayName: String,
    var perfis: List<PerfilEnum>
)
