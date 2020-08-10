package br.com.sigo.consultoria.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.TokenDto
import br.com.sigo.consultoria.dtos.UsuarioDTO
import br.com.sigo.consultoria.enums.PerfilEnum
import br.com.sigo.consultoria.internet.Response
import br.com.sigo.consultoria.presenter.impl.LoginPresenterImpl
import br.com.sigo.consultoria.services.UsuarioService
import br.com.sigo.consultoria.util.LoadingHelper
import br.com.socin.conferenciacupom.internet.Server
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import java.text.MessageFormat

class UsuarioActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private val server: Server by inject()

    private lateinit var tokenDto: TokenDto

    private lateinit var edtCodigo: TextInputEditText
    private lateinit var edtNome: TextInputEditText
    private lateinit var edtSenha: TextInputEditText
    private lateinit var spnTipoUsuario: Spinner
    private lateinit var btnSalvar: Button

    private lateinit var loadingHelper: LoadingHelper
    private lateinit var headers: java.util.HashMap<String, String>

    private var position = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        intent?.let {
            if (it.hasExtra(LoginActivity.EXTRA_USER)) {
                tokenDto = it.getSerializableExtra(LoginActivity.EXTRA_USER) as TokenDto
            }
        }

        if (tokenDto == null || TextUtils.isEmpty(tokenDto.token)) {
            Toast.makeText(this, R.string.usuario_nao_encontrado, Toast.LENGTH_LONG).show()
            finish()
        }

        loadingHelper = LoadingHelper.initialize(
            this, R.id.btnLoadingOk, R.id.loading_container, R.id.tvLoadingStatus,
            R.id.layoutUsuario, R.id.progressBar2
        )

        headers = hashMapOf(
            Pair("Content-Type", "application/json"),
            Pair("Accept", "application/json"),
            Pair("Authorization", tokenDto.token)
        )

        initLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.sair -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initLayout() {
        Log.i(TAG, "initLayout")
        edtCodigo = findViewById(R.id.edtCodigo)
        edtNome = findViewById(R.id.edtNome)
        edtSenha = findViewById(R.id.edtSenha)
        spnTipoUsuario = findViewById(R.id.spnTipoUsuario)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnSalvar.setOnClickListener(this)

        val admin = tokenDto.perfis.filter { p -> p.equals(PerfilEnum.ROLE_ADMIN) }.isNotEmpty()
        var resourceId = R.array.array_usuario
        if (admin) resourceId = R.array.array_admin

        ArrayAdapter.createFromResource(
            this,
            resourceId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnTipoUsuario.adapter = adapter
        }
        spnTipoUsuario.onItemSelectedListener = this
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSalvar -> salvar()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.position = position
        Log.i(
            TAG,
            MessageFormat.format("spinner - this:{0}, position:{1}", this.position, position)
        )
    }

    private fun salvar() {
        Log.i(TAG, "salvar")
        if (TextUtils.isEmpty(edtCodigo.text)) {
            Toast.makeText(this, R.string.codigo_obrigatorio, Toast.LENGTH_LONG).show()
        } else if (TextUtils.isEmpty(edtNome.text)) {
            Toast.makeText(this, R.string.nome_obrigatorio, Toast.LENGTH_LONG).show()
        } else if (TextUtils.isEmpty(edtSenha.text)) {
            Toast.makeText(this, R.string.senha_obrigatoria, Toast.LENGTH_LONG).show()
        } else if (server.isConfigured()) {
            Log.d(TAG, "salvar - validou campos")
            loadingHelper.initializeLoading(getString(R.string.salvando))
            val service: UsuarioService = server.getRetrofit().create(
                UsuarioService::class.java
            )

            val codigo = edtCodigo.text.toString().toInt()
            val nome = edtNome.text.toString()
            val senha = edtSenha.text.toString()
            val usuarioDTO = UsuarioDTO(0, codigo, senha, nome)
            var callback: Call<Response<UsuarioDTO>> =
                service.atualizaUsuarioSistema(usuarioDTO, headers)
            when (position) {
                0 -> callback = service.atualizaUsuarioSistema(usuarioDTO, headers)
                1 -> callback = service.atualizaConsultor(usuarioDTO, headers)
                2 -> callback = service.atualizaAdmin(usuarioDTO, headers)
            }

            callback.enqueue(object :
                Callback<Response<UsuarioDTO>> {
                override fun onFailure(
                    call: Call<Response<UsuarioDTO>>,
                    t: Throwable
                ) {
                    t.message?.let { Log.e(UsuarioActivity.TAG, it) }
                    loadingHelper.stopLoading(
                        R.string.nao_foi_possivel_conectar,
                        View.OnClickListener { loadingHelper.finish() },
                        null
                    )
                }

                override fun onResponse(
                    call: Call<Response<UsuarioDTO>>,
                    response: retrofit2.Response<Response<UsuarioDTO>>
                ) {
                    if (response.code() == 200) {
                        val categorias = response.body()!!.data!!
                        loadingHelper.stopLoading(
                            MessageFormat.format("Usuario salvo com id: {0}", categorias.id),
                            View.OnClickListener { limpar() },
                            null
                        )
                    } else {
                        try {
                            response.errorBody()?.let {
                                val resourceErro = GsonBuilder().create()
                                    .fromJson<br.com.sigo.consultoria.internet.Response<UsuarioDTO>>(
                                        it.string(),
                                        br.com.sigo.consultoria.internet.Response::class.java
                                    )
                                if (resourceErro.errors != null && !resourceErro.errors.isEmpty()) {
                                    val erros = StringBuilder();
                                    resourceErro.errors.forEach { erros.append(it).append("\n") }
                                    loadingHelper.stopLoading(
                                        erros.toString(),
                                        View.OnClickListener { loadingHelper.finish() },
                                        null
                                    )
                                } else {
                                    loadingHelper.stopLoading(
                                        R.string.erro_servidor,
                                        View.OnClickListener { loadingHelper.finish() },
                                        null
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(LoginPresenterImpl.TAG, "trataResultado", e)
                        }
                    }
                }
            })

        }
    }

    private fun limpar() {
        edtCodigo.setText("")
        edtSenha.setText("")
        edtNome.setText("")
        position = 0
        spnTipoUsuario.setSelection(position)
        loadingHelper.finish()
    }

    companion object {
        private val TAG = "UsrActivity"
    }
}
