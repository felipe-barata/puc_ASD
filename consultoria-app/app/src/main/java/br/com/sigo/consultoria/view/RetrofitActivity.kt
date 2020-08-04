package br.com.sigo.consultoria.view

import androidx.appcompat.app.AppCompatActivity
import br.com.sigo.consultoria.view.dialog.DialogCarregamento

abstract class RetrofitActivity : AppCompatActivity(),
    DialogCarregamento.DialogCarregamentoInterface {

    protected var dialog: DialogCarregamento? = null

    fun onError(message: String) {

    }

    fun onError(message: Int) {

    }

    fun onError(message: List<String>) {

    }

}