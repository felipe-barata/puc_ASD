package br.com.sigo.consultoria.presenter

import br.com.sigo.consultoria.view.listeners.LoginListener
import org.koin.core.KoinComponent

interface LoginPresenter : KoinComponent {

    fun login(
        user: Int, pass: String, listener: LoginListener
    )

}