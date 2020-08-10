package br.com.socin.conferenciacupom.internet

import br.com.sigo.consultoria.domain.Configuration
import br.com.sigo.consultoria.internet.RetrofitBuilder
import br.com.sigo.consultoria.util.PreferencesUtil
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit

class Server : KoinComponent {

    private val preferencesUtil: PreferencesUtil by inject()

    private lateinit var configuration: Configuration
    private var configured = false
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitNorma: Retrofit

    fun configure() : Boolean{
        val cfg = preferencesUtil.getConfigurationFromPreferences()
        if (null != cfg) {
            configure(cfg)
            return true
        }
        return false
    }

    fun configure(configuration: Configuration) {
        configured = true
        this.configuration = configuration
        retrofit =
            RetrofitBuilder().getRetrofit(configuration, configuration.baseUrl)

        retrofitNorma =
            RetrofitBuilder().getRetrofit(configuration, configuration.normaUrl)
    }

    fun isConfigured(): Boolean {
        if (!configured) {
            configured = configure()
        }
        return configured
    }

    fun getRetrofit(): Retrofit {
        return retrofit
    }

    fun getRetrofitNorma(): Retrofit {
        return retrofitNorma
    }
}