package br.com.sigo.consultoria.domain

import java.io.Serializable

data class Configuration(
    var baseUrl: String,
    var connectTimeout: Int,
    var readTimeout: Int,
    var writeTimeout: Int,
    var normaUrl: String?
) : Serializable