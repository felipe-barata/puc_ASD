package br.com.sigo.consultoria.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import br.com.sigo.consultoria.domain.Configuration

class PreferencesUtil(val context: Context) {

    fun saveJsonUserToPreferences(user: String) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString("currentUser", user)
        editor.apply()
    }

    fun getJsonUserFromPreferences(): String? {
        val myPrefs = getSharedPreferences(context)
        val user = myPrefs.getString("currentUser", null)
        return user
    }

    fun getConfigurationFromPreferences(): Configuration? {
        val prefs = getDefaultSharedPreferences(context)
        var configuration: Configuration? = null
        val baseUrl = prefs.getString("url", null)
        val connectTimeout = prefs.getString("connect", "10")
        val readTimeout = prefs.getString("read", "3")
        val writeTimeout = prefs.getString("write", "3")
        if (baseUrl != null && !baseUrl.isEmpty()) {
            configuration = Configuration(
                baseUrl,
                connectTimeout!!.toInt(),
                readTimeout!!.toInt(),
                writeTimeout!!.toInt()
            )
        }
        return configuration
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PATH,
            Context.MODE_PRIVATE
        )
    }

    private fun getDefaultSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object {
        val PATH = "br.com.socin.consultoria"
    }
}