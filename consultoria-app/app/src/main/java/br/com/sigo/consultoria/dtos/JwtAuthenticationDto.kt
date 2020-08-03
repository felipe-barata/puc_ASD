package br.com.sigo.consultoria.dtos

data class JwtAuthenticationDto(var codigo: Int, var senha: String) {
    override fun toString(): String {
        return "JwtAuthenticationRequestDto [codigo=$codigo, senha=$senha]"
    }
}