package br.com.sigo.consultoria.presenter.impl

import android.util.Log
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.JwtAuthenticationDto
import br.com.sigo.consultoria.dtos.TokenDto
import br.com.sigo.consultoria.presenter.LoginPresenter
import br.com.sigo.consultoria.services.LoginService
import br.com.sigo.consultoria.util.PreferencesUtil
import br.com.sigo.consultoria.view.listeners.LoginListener
import br.com.socin.conferenciacupom.internet.Server
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.MessageFormat

class LoginPresenterImpl : LoginPresenter {

    private val preferencesUtil: PreferencesUtil by inject()
    private val gson: Gson by inject()

    private val server: Server by inject()

    override fun login(user: Int, pass: String, listener: LoginListener) {
        Log.i(TAG, MessageFormat.format("user: {0}", user))

        if (server.isConfigured()) {
            val service: LoginService = server.getRetrofit().create(
                LoginService::class.java
            )

            val loginDto = JwtAuthenticationDto(user, pass)
            val callback: Call<br.com.sigo.consultoria.internet.Response<TokenDto>> =
                service.login(loginDto)
            callback.enqueue(object :
                Callback<br.com.sigo.consultoria.internet.Response<TokenDto>> {
                override fun onFailure(
                    call: Call<br.com.sigo.consultoria.internet.Response<TokenDto>>,
                    t: Throwable
                ) {
                    listener.onError(R.string.nao_foi_possivel_conectar)
                }

                override fun onResponse(
                    call: Call<br.com.sigo.consultoria.internet.Response<TokenDto>>,
                    response: Response<br.com.sigo.consultoria.internet.Response<TokenDto>>
                ) {
                    trataResultado(response, listener)
                }
            })
        } else{
            listener.onError(R.string.nao_configurado)
        }

    }

    private fun trataResultado(
        response: Response<br.com.sigo.consultoria.internet.Response<TokenDto>>,
        listener: LoginListener
    ) {
        if (response.code() == 200) {
            preferencesUtil.saveJsonUserToPreferences(gson.toJson(response.body()!!.data))
            listener.onUser(response.body()!!.data!!)
        } else {
            try {
                response.errorBody()?.let {
                    val resourceErro = GsonBuilder().create()
                        .fromJson<br.com.sigo.consultoria.internet.Response<TokenDto>>(
                            it.string(),
                            br.com.sigo.consultoria.internet.Response::class.java
                        )
                    if (resourceErro.errors != null && !resourceErro.errors.isEmpty()) {
                        listener.onError(resourceErro.errors)
                    } else {
                        listener.onError(R.string.erro_servidor)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "trataResultado", e)
            }
        }
    }

    companion object {
        val TAG = "LGNPresenter"
    }
}