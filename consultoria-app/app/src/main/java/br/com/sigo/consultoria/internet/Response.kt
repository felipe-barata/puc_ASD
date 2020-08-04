package br.com.sigo.consultoria.internet

data class Response<T>(val data: T?, val errors: List<String>?)