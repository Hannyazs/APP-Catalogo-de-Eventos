package com.example.ap2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2.R
import com.example.ap2.model.Inscricao

class InscricaoAdapter(
    private val lista: List<Inscricao>
) : RecyclerView.Adapter<InscricaoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){

        val texto =
            itemView.findViewById<TextView>(R.id.tvInscricao)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_inscricao,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = lista[position]

        holder.texto.text =
            """ ${item.nomeEvento}
            Data: ${item.data}
            Horário: ${item.horario}
            Local: ${item.local}
            """.trimIndent()
    }

    override fun getItemCount() =
        lista.size
}