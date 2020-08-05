package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class PageDTO<T>(
    var totalPages: Int,
    var totalElements: Int,
    var number: Int,
    var size: Int,
    var content: ArrayList<T>,
    var first: Boolean,
    var last: Boolean,
    var numberOfElements: Int,
    var empty: Boolean
) : Serializable