package br.com.sigo.consultoria.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.TokenDto
import br.com.sigo.consultoria.presenter.LoginPresenter
import br.com.sigo.consultoria.util.LoadingHelper
import br.com.sigo.consultoria.view.listeners.LoginListener
import org.koin.android.ext.android.inject
import java.text.MessageFormat

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginListener,
    TextView.OnEditorActionListener {

    private val loginPresenter: LoginPresenter by inject()

    private lateinit var edtUsername: EditText
    private lateinit var edtSenha: EditText

    private lateinit var loadingHelper: LoadingHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //verifica se já foi configurado URL do servidor
        val defaultSharedPreferences = getDefaultSharedPreferences(this)
        if (!defaultSharedPreferences.contains("url")) {
            Log.i(TAG, "primeiro acesso, abrir configuracao")
            config()
        }

        edtSenha = findViewById(R.id.edtSenha)
        edtUsername = findViewById(R.id.edtUsername)

        edtUsername.setOnEditorActionListener(this)
        edtSenha.setOnEditorActionListener(this)

        findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
        findViewById<Button>(R.id.btnConfig).setOnClickListener(this)

        loadingHelper = LoadingHelper.initialize(
            this, R.id.btnLoadingOk, R.id.loading_container, R.id.tvLoadingStatus,
            R.id.loginLayout, R.id.progressBar2
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {
                login()
            }
            R.id.btnConfig -> {
                config()
            }
        }
    }

    private fun config() {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    private fun login() {
        Log.i(TAG, "clicou no login")
        val senha = edtSenha.text.toString()
        val username = edtUsername.text.toString()
        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, R.string.senha_obrigatoria, Toast.LENGTH_LONG).show()
            edtSenha.requestFocus()
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, R.string.username_obrigatorio, Toast.LENGTH_LONG).show()
            edtUsername.requestFocus()
        } else {
            if (verificaConexao()) {
                loadingHelper.initializeLoading(getString(R.string.autenticando))
                loginPresenter.login(username.toInt(), senha, this)
            } else {
                Toast.makeText(this, R.string.sem_conexao, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onUser(tokenDto: TokenDto) {
        Log.d(TAG, "onUser")
        loadingHelper.finish()
        Toast.makeText(
            this,
            MessageFormat.format("Bem vindo {0}!", tokenDto.displayName),
            Toast.LENGTH_LONG
        ).show()
        val i = Intent(this, MenuActivity::class.java)
        i.putExtra(EXTRA_USER, tokenDto)
        startActivity(i)
        finish()
    }

    override fun onError(message: String) {
        Log.d(TAG, String.format("onError - message: %s", message))
        loadingHelper.stopLoading(message, View.OnClickListener { loadingHelper.finish() }, null)
    }

    override fun onError(message: Int) {
        Log.d(TAG, String.format("onError - message: %s", message))
        loadingHelper.stopLoading(message, View.OnClickListener { loadingHelper.finish() }, null)
    }

    override fun onError(message: List<String>) {
        val strB = StringBuilder()
        for (s in message) {
            strB.append(s).append("\n")
        }
        onError(strB.toString())
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
            when (v!!.id) {
                R.id.edtUsername -> {
                    edtSenha.requestFocus()
                }
                R.id.edtSenha -> {
                    login()
                }
            }
        }
        return false
    }

    private fun verificaConexao(): Boolean {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

    companion object {
        private val TAG = "LGNActivity"
        public val EXTRA_USER = "user"
    }
}
