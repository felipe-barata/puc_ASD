package br.com.sigo.consultoria.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.sigo.consultoria.R
import br.com.sigo.consultoria.dtos.NormasDTO

class NormaAdapter(val context: Context, var list: ArrayList<NormasDTO>) :
    RecyclerView.Adapter<NormaAdapter.ItemNormaViewHolder>() {

    class ItemNormaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var tvDescCategoria: TextView
        var tvDescTipo: TextView
        var tvTitulo: TextView

        init {
            tvDescCategoria = itemView.findViewById(R.id.tvDescCategoria)
            tvDescTipo = itemView.findViewById(R.id.tvDescTipo)
            tvTitulo = itemView.findViewById(R.id.tvTitulo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNormaViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.item_normas, parent, false)
        return ItemNormaViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemNormaViewHolder, position: Int) {
        var item = list.get(position)
        holder.tvDescCategoria.setText(item.categoria)
        holder.tvDescTipo.setText(item.tipo)
        holder.tvTitulo.setText(item.titulo)
    }
}