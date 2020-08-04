package br.com.sigo.consultoria.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import br.com.sigo.consultoria.R

class DialogCarregamento : DialogFragment(), View.OnClickListener {

    private var listener: DialogCarregamentoInterface? = null

    private lateinit var btnCancelar: Button
    private lateinit var btnTentarNovamente: Button
    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_loading, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.tvTexto)
        btnCancelar = view.findViewById(R.id.btnCancelar)
        btnTentarNovamente = view.findViewById(R.id.btnTentarNovamente)
        progressBar = view.findViewById(R.id.progressBar)

        textView.setText(arguments?.getString("texto"))

        btnCancelar.setOnClickListener(this)
        btnTentarNovamente.setOnClickListener(this)

        btnCancelar.visibility = View.GONE
        btnTentarNovamente.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCancelar -> listener?.cancelar()
            R.id.btnTentarNovamente -> listener?.tentarNovamente()
        }
    }

    interface DialogCarregamentoInterface {

        fun tentarNovamente()

        fun cancelar()

    }

    companion object {
        fun newInstance(texto: String, listener: DialogCarregamentoInterface): DialogCarregamento {
            val frag = DialogCarregamento()
            frag.listener = listener
            val args = Bundle()
            args.putString("texto", texto)
            frag.arguments = args
            return frag
        }
    }
}