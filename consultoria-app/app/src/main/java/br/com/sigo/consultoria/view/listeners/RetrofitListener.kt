package br.com.sigo.consultoria.view.listeners

interface RetrofitListener {

    fun onError(message: String)

    fun onError(message: Int)

    fun onError(message: List<String>)

}