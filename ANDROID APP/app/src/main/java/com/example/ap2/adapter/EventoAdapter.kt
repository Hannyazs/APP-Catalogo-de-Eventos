package com.example.ap2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2.R
import com.example.ap2.model.Evento

class EventoAdapter(
    private var lista: List<Evento>,
    private val onClick: (Evento) -> Unit,
    private val onEditClick: ((Evento) -> Unit)? = null,
    private val onDeleteClick: ((Evento) -> Unit)? = null
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome = itemView.findViewById<TextView>(R.id.tvNomeEvento)
        val data = itemView.findViewById<TextView>(R.id.tvDataEvento)
        val local = itemView.findViewById<TextView>(R.id.tvLocalEvento)

        // Referências aos botões que você criará no item_evento.xml
        val btnEditar = itemView.findViewById<ImageButton>(R.id.btnEditarEvento)
        val btnDeletar = itemView.findViewById<ImageButton>(R.id.btnDeletarEvento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = lista[position]

        holder.nome.text = evento.nome
        holder.data.text = evento.data
        holder.local.text = evento.local

        // Clique no item inteiro (vai para detalhes)
        holder.itemView.setOnClickListener {
            onClick(evento)
        }

        // Configura o botão Editar (esconde se não for admin)
        if (onEditClick != null) {
            holder.btnEditar.visibility = View.VISIBLE
            holder.btnEditar.setOnClickListener { onEditClick.invoke(evento) }
        } else {
            holder.btnEditar.visibility = View.GONE
        }

        // Configura o botão Deletar (esconde se não for admin)
        if (onDeleteClick != null) {
            holder.btnDeletar.visibility = View.VISIBLE
            holder.btnDeletar.setOnClickListener { onDeleteClick.invoke(evento) }
        } else {
            holder.btnDeletar.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = lista.size
}