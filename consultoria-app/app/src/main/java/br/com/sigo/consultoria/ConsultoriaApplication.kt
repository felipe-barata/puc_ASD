package br.com.sigo.consultoria

import androidx.multidex.MultiDexApplication
import br.com.sigo.consultoria.di.controllerModule
import br.com.sigo.consultoria.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConsultoriaApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ConsultoriaApplication)
            modules(listOf(controllerModule, uiModule))
        }
    }
}