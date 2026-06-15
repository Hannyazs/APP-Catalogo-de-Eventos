package com.example.ap2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class DetalhesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes)

        val id = intent.getIntExtra("id", 0)
        val nome = intent.getStringExtra("nome")
        val data = intent.getStringExtra("data")
        val local = intent.getStringExtra("local")
        val descricao = intent.getStringExtra("descricao")
        val nomeAluno = intent.getStringExtra("nomeAluno")
        val isAdmin = intent.getBooleanExtra("isAdmin", false)

        findViewById<TextView>(R.id.tvNome).text = nome
        findViewById<TextView>(R.id.tvData).text = data
        findViewById<TextView>(R.id.tvLocal).text = local
        findViewById<TextView>(R.id.tvDescricao).text = descricao

        val btnInscrever = findViewById<Button>(R.id.btnInscrever)

        if (isAdmin) {
            btnInscrever.visibility = android.view.View.GONE
        } else {
            btnInscrever.visibility = android.view.View.VISIBLE
            btnInscrever.setOnClickListener {
                val intent = Intent(this, InscricaoActivity::class.java)
                intent.putExtra("evento", nome)
                intent.putExtra("eventoId", id)
                intent.putExtra("nomeAluno", nomeAluno)
                startActivity(intent)
            }
        }

        val btnCompartilhar =
            findViewById<Button>(R.id.btnCompartilhar)

        btnCompartilhar.setOnClickListener {

            val texto = """
                Evento: $nome
                Data: $data
                Local: $local
                Descrição: $descricao
            """.trimIndent()

            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/plain"

            intent.putExtra(
                Intent.EXTRA_TEXT,
                texto
            )

            startActivity(
                Intent.createChooser(
                    intent,
                    "Compartilhar Evento"
                )
            )
        }
    }
}