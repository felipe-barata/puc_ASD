package br.com.sigo.consultoria.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.CategoriaDTO
import br.com.sigo.consultoria.dtos.PageDTO
import br.com.sigo.consultoria.dtos.PaginacaoDTO
import br.com.sigo.consultoria.dtos.TokenDto
import br.com.sigo.consultoria.internet.Response
import br.com.sigo.consultoria.services.CategoriaService
import br.com.sigo.consultoria.util.LoadingHelper
import br.com.sigo.consultoria.util.PreferencesUtil
import br.com.sigo.consultoria.view.adapter.CategoriaAdapter
import br.com.socin.conferenciacupom.internet.Server
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import java.text.MessageFormat

class CategoriaActivity : AppCompatActivity(), View.OnClickListener {

    private val gson: Gson by inject()

    private val server: Server by inject()

    private val preferencesUtil: PreferencesUtil by inject()

    private lateinit var textInputCategoria: TextInputEditText
    private lateinit var textInputDescricaoCategoria: TextInputEditText
    private lateinit var btnSalvar: Button
    private lateinit var recycler: RecyclerView
    private lateinit var loadingHelper: LoadingHelper
    private lateinit var headers: java.util.HashMap<String, String>
    private lateinit var tokenDto: TokenDto
    private lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        textInputCategoria = findViewById(R.id.textInputCategoria)
        textInputDescricaoCategoria = findViewById(R.id.textInputDescricaoCategoria)
        btnSalvar = findViewById(R.id.btnSalvar)
        recycler = findViewById(R.id.recycler)

        btnSalvar.setOnClickListener(this)

        loadingHelper = LoadingHelper.initialize(
            this, R.id.btnLoadingOk, R.id.loading_container, R.id.tvLoadingStatus,
            R.id.categoriaLayout, R.id.progressBar2
        )

        val userFromPreferences = preferencesUtil.getJsonUserFromPreferences()
        tokenDto = gson.fromJson(userFromPreferences, TokenDto::class.java)
        if (tokenDto != null) {
            headers = hashMapOf(
                Pair("Content-Type", "application/json"),
                Pair("Accept", "application/json"),
                Pair("Authorization", tokenDto.token)
            )
        } else {
            Toast.makeText(this, R.string.usuario_nao_encontrado, Toast.LENGTH_LONG).show()
            finish()
        }

        categoriaAdapter = CategoriaAdapter(this, arrayListOf())
        recycler.tag = "items"
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = categoriaAdapter

        carregarCategorias()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sync_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.sync -> carregarCategorias()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSalvar -> salvar()
        }
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
                        val categorias = response.body()!!.data!!.content
                        categoriaAdapter.list = categorias
                        categoriaAdapter.notifyDataSetChanged()
                        loadingHelper.finish()
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

    private fun salvar() {
        Log.i(TAG, "salvar")
        if (TextUtils.isEmpty(textInputDescricaoCategoria.text)) {
            Toast.makeText(this, R.string.descricao_obrigatoria, Toast.LENGTH_LONG).show()
        } else {
            if (server.isConfigured()) {
                loadingHelper.initializeLoading(getString(R.string.salvando))
                val service: CategoriaService = server.getRetrofit().create(
                    CategoriaService::class.java
                )
                val categoriaDTO = CategoriaDTO(0, textInputDescricaoCategoria.text.toString())
                val callback: Call<Response<CategoriaDTO>> =
                    service.atualizarCategoria(categoriaDTO, headers)
                callback.enqueue(object :
                    Callback<Response<CategoriaDTO>> {
                    override fun onFailure(
                        call: Call<Response<CategoriaDTO>>,
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
                        call: Call<Response<CategoriaDTO>>,
                        response: retrofit2.Response<Response<CategoriaDTO>>
                    ) {
                        if (response.code() == 200) {
                            textInputDescricaoCategoria.setText("")
                            textInputCategoria.setText("")
                            val categorias = response.body()!!.data!!
                            loadingHelper.stopLoading(
                                MessageFormat.format("Categoria salva com id: {0}", categorias.id),
                                View.OnClickListener { carregarCategorias() },
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
                })
            }

        }
    }

    companion object {
        private val TAG = "CatActivity"
    }
}
