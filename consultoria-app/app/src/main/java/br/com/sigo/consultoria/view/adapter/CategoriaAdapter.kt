package br.com.sigo.consultoria.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.CategoriaDTO

class CategoriaAdapter(val context: Context, var list: ArrayList<CategoriaDTO>) :
    RecyclerView.Adapter<CategoriaAdapter.ItemCategoriaViewHolder>() {

    class ItemCategoriaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvCodigo: TextView
        var tvDescricao: TextView

        init {
            tvCodigo = itemView.findViewById(R.id.tvCodigo)
            tvDescricao = itemView.findViewById(R.id.tvDescricao)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoriaViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false)
        return ItemCategoriaViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemCategoriaViewHolder, position: Int) {
        val item = list.get(position)
        holder.tvCodigo.text = item.id.toString()
        holder.tvDescricao.text = item.descricao
    }
}