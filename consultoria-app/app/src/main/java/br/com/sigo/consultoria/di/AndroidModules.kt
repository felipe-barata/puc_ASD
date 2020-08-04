package br.com.sigo.consultoria.di

import android.content.Context
import br.com.sigo.consultoria.presenter.LoginPresenter
import br.com.sigo.consultoria.presenter.impl.LoginPresenterImpl
import br.com.sigo.consultoria.util.PreferencesUtil
import br.com.socin.conferenciacupom.internet.Server
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.module.Module
import org.koin.dsl.module

fun startKoin(context: Context, vararg modules: Module) {
    org.koin.core.context.startKoin {
        androidLogger()
        androidContext(context)
        val md = arrayListOf<Module>()
        if (modules != null && !modules.isEmpty()) {
            modules.forEach { m -> md.add(m) }
        }
        md.addAll(listOf(uiModule, controllerModule))
        modules(md)
    }
}

val uiModule = module {
    factory<LoginPresenter> { LoginPresenterImpl() }
}

val controllerModule = module {
    factory { PreferencesUtil(androidContext()) }
    single { Server() }
    single<Gson> { GsonBuilder().create() }
}