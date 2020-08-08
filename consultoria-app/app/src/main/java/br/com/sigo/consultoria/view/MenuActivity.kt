package br.com.sigo.consultoria.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.TokenDto
import br.com.sigo.consultoria.enums.PerfilEnum
import br.com.sigo.consultoria.view.LoginActivity.Companion.EXTRA_USER

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tokenDto: TokenDto

    private lateinit var btnCategoria: CardView
    private lateinit var btnEmpresa: CardView
    private lateinit var btnUsuario: CardView
    private lateinit var btnConsultaNormas: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCategoria = findViewById<CardView>(R.id.btnCategoria)
        btnEmpresa = findViewById<CardView>(R.id.btnEmpresa)
        btnUsuario = findViewById<CardView>(R.id.btnUsuario)
        btnConsultaNormas = findViewById<CardView>(R.id.btnConsultaNormas)

        btnCategoria.setOnClickListener(this)
        btnEmpresa.setOnClickListener(this)
        btnUsuario.setOnClickListener(this)
        btnConsultaNormas.setOnClickListener(this)

        btnCategoria.visibility = View.GONE
        btnEmpresa.visibility = View.GONE
        btnUsuario.visibility = View.GONE

        intent?.let {
            if (it.hasExtra(EXTRA_USER)) {
                tokenDto = it.getSerializableExtra(EXTRA_USER) as TokenDto
            }
        }

        verificaPerfilUsuario()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> voltar()
            R.id.sair -> voltar()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        voltar()
    }

    private fun voltar() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun verificaPerfilUsuario() {
        if (tokenDto.perfis.filter { it.equals(PerfilEnum.ROLE_USUARIO) }.isNotEmpty()) {
            btnCategoria.visibility = View.VISIBLE
            btnEmpresa.visibility = View.VISIBLE
            btnUsuario.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCategoria -> btnCategoria()
            R.id.btnEmpresa -> btnEmpresa()
            R.id.btnUsuario -> btnUsuario()
            R.id.btnConsultaNormas -> btnConsultaNormas()
        }
    }

    fun btnCategoria() {
        Log.i(TAG, "btnCategoria")
        val i = Intent(this, CategoriaActivity::class.java)
        startActivity(i)
    }

    fun btnEmpresa() {
        Log.i(TAG, "btnEmpresa")
        val i = Intent(this, EmpresaActivity::class.java)
        i.putExtra(EXTRA_USER, tokenDto)
        startActivity(i)
    }

    fun btnUsuario() {
        Log.i(TAG, "btnUsuario")
        val i = Intent(this, UsuarioActivity::class.java)
        i.putExtra(EXTRA_USER, tokenDto)
        startActivity(i)
    }

    fun btnConsultaNormas() {
        Log.i(TAG, "btnConsultaNormas")
        val i = Intent(this, ConsultaNormasActivity::class.java)
        i.putExtra(EXTRA_USER, tokenDto)
        startActivity(i)
    }

    companion object {
        private val TAG = "MenuActivity"
    }
}
