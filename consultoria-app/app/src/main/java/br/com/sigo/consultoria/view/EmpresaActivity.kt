package br.com.sigo.consultoria.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.*
import br.com.sigo.consultoria.internet.Response
import br.com.sigo.consultoria.services.CategoriaService
import br.com.sigo.consultoria.services.EmpresaService
import br.com.sigo.consultoria.util.LoadingHelper
import br.com.socin.conferenciacupom.internet.Server
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import java.text.MessageFormat
import java.util.*

class EmpresaActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private val server: Server by inject()
    private lateinit var tokenDto: TokenDto

    private lateinit var spnCategoria: Spinner

    private lateinit var edtCnpj: TextInputEditText
    private lateinit var edtNome: TextInputEditText
    private lateinit var edtCodigoPostal: TextInputEditText
    private lateinit var edtCodigoPais: TextInputEditText
    private lateinit var edtEstado: TextInputEditText
    private lateinit var edtCidade: TextInputEditText
    private lateinit var edtBairro: TextInputEditText
    private lateinit var edtEndereco: TextInputEditText
    private lateinit var edtNumero: TextInputEditText
    private lateinit var btnSalvar: Button
    private lateinit var loadingHelper: LoadingHelper

    private lateinit var headers: HashMap<String, String>
    private var categorias: ArrayList<CategoriaDTO>? = null

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa)

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
            R.id.layoutEmpresa, R.id.progressBar2
        )

        headers = hashMapOf(
            Pair("Content-Type", "application/json"),
            Pair("Accept", "application/json"),
            Pair("Authorization", tokenDto.token)
        )

        carregarCategorias()
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

    private fun iniciarLayout() {
        spnCategoria = findViewById(R.id.spnCategoria)
        edtCnpj = findViewById(R.id.edtCnpj)
        edtNome = findViewById(R.id.edtNome)
        edtCodigoPostal = findViewById(R.id.edtCodigoPostal)
        edtCodigoPais = findViewById(R.id.edtCodigoPais)
        edtEstado = findViewById(R.id.edtEstado)
        edtCidade = findViewById(R.id.edtCidade)
        edtBairro = findViewById(R.id.edtBairro)
        edtEndereco = findViewById(R.id.edtEndereco)
        edtNumero = findViewById(R.id.edtNumero)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnSalvar.setOnClickListener(this)

        if(categorias != null){
            val map = categorias!!.map { t -> MessageFormat.format("{0} - {1}", t.id, t.descricao) }
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, map)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnCategoria.adapter = arrayAdapter
            spnCategoria.onItemSelectedListener = this
        }
    }

    private fun salvar() {
        Log.i(TAG, "salvar")
        if (validar()) {
            Log.d(TAG, "salvar - validou")
            if (server.isConfigured()) {
                loadingHelper.initializeLoading(getString(R.string.salvando))
                val service: EmpresaService = server.getRetrofit().create(
                    EmpresaService::class.java
                )
                val categoria = categorias!!.get(position)
                val cnpj = edtCnpj.text.toString()
                val nome = edtNome.text.toString()
                val postal = edtCodigoPostal.text.toString()
                val pais = edtCodigoPais.text.toString().toInt()
                val estado = edtEstado.text.toString()
                val cidade = edtCidade.text.toString()
                val bairro = edtBairro.text.toString()
                val endereco = edtEndereco.text.toString()
                val numero = edtNumero.text.toString()
                val empresa = EmpresaDTO(
                    0,
                    cnpj,
                    nome,
                    postal,
                    pais,
                    estado,
                    cidade,
                    bairro,
                    endereco,
                    numero,
                    categoria.id
                )
                var callback: Call<Response<EmpresaDTO>> =
                    service.atualizaEmpresa(empresa, headers)

                callback.enqueue(object :
                    Callback<Response<EmpresaDTO>> {
                    override fun onFailure(
                        call: Call<Response<EmpresaDTO>>,
                        t: Throwable
                    ) {
                        t.message?.let { Log.e(TAG, it) }
                        loadingHelper.stopLoading(
                            R.string.nao_foi_possivel_conectar,
                            View.OnClickListener { loadingHelper.finish() },
                            null
                        )
                    }

                    override fun onResponse(
                        call: Call<Response<EmpresaDTO>>,
                        response: retrofit2.Response<Response<EmpresaDTO>>
                    ) {
                        if (response.code() == 200) {
                            val categorias = response.body()!!.data!!
                            loadingHelper.stopLoading(
                                MessageFormat.format("Empresa salva com id: {0}", categorias.id),
                                View.OnClickListener { limpar() },
                                null
                            )
                        } else {
                            try {
                                response.errorBody()?.let {
                                    val resourceErro = GsonBuilder().create()
                                        .fromJson<br.com.sigo.consultoria.internet.Response<EmpresaDTO>>(
                                            it.string(),
                                            br.com.sigo.consultoria.internet.Response::class.java
                                        )
                                    if (resourceErro.errors != null && !resourceErro.errors.isEmpty()) {
                                        val erros = StringBuilder();
                                        resourceErro.errors.forEach {
                                            erros.append(it).append("\n")
                                        }
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
                                Log.e(TAG, "trataResultado", e)
                            }
                        }
                    }
                })
            }
        }
    }

    private fun limpar() {
        edtCnpj.setText("")
        edtNome.setText("")
        edtCodigoPostal.setText("")
        edtCodigoPais.setText("")
        edtEstado.setText("")
        edtCidade.setText("")
        edtBairro.setText("")
        edtEndereco.setText("")
        edtNumero.setText("")
        position = 0
        spnCategoria.setSelection(position)
        loadingHelper.finish()
    }

    private fun validar(): Boolean {
        if (TextUtils.isEmpty(edtCnpj.text)) {
            Log.w(TAG, "validar - nao informou cnpj")
            Toast.makeText(this, R.string.cnpj_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtNome.text)) {
            Log.w(TAG, "validar - nao informou nome")
            Toast.makeText(this, R.string.nome_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtCodigoPostal.text)) {
            Log.w(TAG, "validar - nao informou codigo postal")
            Toast.makeText(this, R.string.postal_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtCodigoPais.text)) {
            Log.w(TAG, "validar - nao informou pais")
            Toast.makeText(this, R.string.pais_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtEstado.text)) {
            Log.w(TAG, "validar - nao informou estado")
            Toast.makeText(this, R.string.estado_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtCidade.text)) {
            Log.w(TAG, "validar - nao informou cidade")
            Toast.makeText(this, R.string.cidade_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtBairro.text)) {
            Log.w(TAG, "validar - nao informou bairro")
            Toast.makeText(this, R.string.bairro_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        } else if (TextUtils.isEmpty(edtNumero.text)) {
            Log.w(TAG, "validar - nao informou numero")
            Toast.makeText(this, R.string.numero_obrigatorio, Toast.LENGTH_LONG).show()
            return false
        }
        return true;
    }

    private fun carregarCategorias() {
        Log.i(TAG, "carregarCategorias")
        loadingHelper.initializeLoading(getString(R.string.consultando))

        if (server.isConfigured()) {
            val service: CategoriaService = server.getRetrofit().create(
                CategoriaService::class.java
            )

            val pageDTO = PaginacaoDTO(0, 50)
            val callback: Call<Response<PageDTO<CategoriaDTO>>> =
                service.retornaTodasCategorias(pageDTO, headers)
            callback.enqueue(object :
                Callback<Response<PageDTO<CategoriaDTO>>> {
                override fun onFailure(
                    call: Call<Response<PageDTO<CategoriaDTO>>>,
                    t: Throwable
                ) {
                    t.message?.let { Log.e(TAG, it) }
                    loadingHelper.stopLoading(
                        R.string.nao_foi_possivel_conectar,
                        View.OnClickListener { loadingHelper.finish() },
                        null
                    )
                }

                override fun onResponse(
                    call: Call<Response<PageDTO<CategoriaDTO>>>,
                    response: retrofit2.Response<Response<PageDTO<CategoriaDTO>>>
                ) {
                    if (response.code() == 200) {
                        categorias = response.body()!!.data!!.content
                        if (categorias == null || categorias!!.isEmpty()) {
                            loadingHelper.stopLoading(
                                R.string.nenhuma_categoria,
                                View.OnClickListener { finish() },
                                null
                            )
                        } else {
                            loadingHelper.finish()
                            iniciarLayout()
                        }
                    } else {
                        val erros = StringBuilder();
                        response.body()!!.errors!!.forEach { erros.append(it).append("\n") }
                        loadingHelper.stopLoading(
                            erros.toString(),
                            View.OnClickListener { loadingHelper.finish() },
                            null
                        )
                    }
                }
            })
        }

    }

    companion object {
        private val TAG = "EmprActivity"
    }
}
