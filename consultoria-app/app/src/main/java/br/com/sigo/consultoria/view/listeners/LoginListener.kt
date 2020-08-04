package br.com.sigo.consultoria.view.listeners

import br.com.sigo.consultoria.dtos.TokenDto

interface LoginListener : RetrofitListener{

    fun onUser(tokenDto: TokenDto)
}