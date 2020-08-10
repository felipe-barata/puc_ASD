package br.com.sigo.consultoria.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.ConsultaNormaDTO
import br.com.sigo.consultoria.dtos.NormasDTO
import br.com.sigo.consultoria.dtos.PageDTO
import br.com.sigo.consultoria.internet.Response
import br.com.sigo.consultoria.services.NormaService
import br.com.sigo.consultoria.util.LoadingHelper
import br.com.sigo.consultoria.view.adapter.NormaAdapter
import br.com.socin.conferenciacupom.internet.Server
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback

class ConsultaNormasActivity : AppCompatActivity() {

    private val server: Server by inject()

    private lateinit var loadingHelper: LoadingHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var normaAdapter: NormaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_normas)

        recyclerView = findViewById(R.id.recyclerView)

        loadingHelper = LoadingHelper.initialize(
            this, R.id.btnLoadingOk, R.id.loading_container, R.id.tvLoadingStatus,
            R.id.layoutConsultar, R.id.progressBar2
        )

        normaAdapter = NormaAdapter(this, arrayListOf())
        recyclerView.tag = "items"
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = normaAdapter

        consultar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sync_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.sync -> consultar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun consultar() {
        Log.i(TAG, "consultar")
        loadingHelper.initializeLoading(getString(R.string.consultando))

        if (server.isConfigured()) {
            val service: NormaService = server.getRetrofitNorma().create(
                NormaService::class.java
            )

            val consultaNormaDTO = ConsultaNormaDTO(0, 25, "ASC", "titulo")
            val callback: Call<Response<PageDTO<NormasDTO>>> =
                service.consultarNormas(consultaNormaDTO)
            callback.enqueue(object :
                Callback<Response<PageDTO<NormasDTO>>> {
                override fun onFailure(
                    call: Call<Response<PageDTO<NormasDTO>>>,
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
                    call: Call<Response<PageDTO<NormasDTO>>>,
                    response: retrofit2.Response<Response<PageDTO<NormasDTO>>>
                ) {
                    if (response.code() == 200) {
                        val categorias = response.body()!!.data!!.content
                        normaAdapter.list = categorias
                        normaAdapter.notifyDataSetChanged()
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

    companion object {
        private val TAG = "NRMActivity"
    }
}
