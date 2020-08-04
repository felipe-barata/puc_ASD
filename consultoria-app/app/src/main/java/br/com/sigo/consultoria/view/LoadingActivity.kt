package br.com.sigo.consultoria.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import br.com.sigo.consultoria.R

class LoadingActivity : AppCompatActivity() {

    private lateinit var text: EditText
    private lateinit var btnCancelar: Button
    private lateinit var btnTentarNovamente: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        text = findViewById(R.id.tvTexto)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnTentarNovamente = findViewById(R.id.btnTentarNovamente)
        progressBar = findViewById(R.id.progressBar)

        val textoCarregamento = intent.getStringExtra(TEXTO_CARREGAMENTO)

    }

    companion object {
        private val TAG = "LDNActivity"

        val TEXTO_CARREGAMENTO = "TEXTO_CARREGAMENTO";
    }
}
