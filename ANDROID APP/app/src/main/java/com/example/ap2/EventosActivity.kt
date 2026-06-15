package com.example.ap2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2.adapter.EventoAdapter
import com.example.ap2.model.Evento
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.ap2.api.RetrofitClient
import android.widget.Button

class EventosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eventos)

        val nome =
            intent.getStringExtra("nome")

        val tvBoasVindas =
            findViewById<TextView>(R.id.tvBoasVindas)

        tvBoasVindas.text =
            "Bem-vindo, $nome"

        val recycler =
            findViewById<RecyclerView>(R.id.recyclerEventos)

        recycler.layoutManager =
            LinearLayoutManager(this)

        val btnFragment =
            findViewById<Button>(
                R.id.btnMostrarFragment )

        val tvEmBreve = findViewById<TextView>(R.id.tvEmBreve)

        btnFragment.setOnClickListener {
            tvEmBreve.visibility = android.view.View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    EventosFuturosFragment() )
                .commit()
        }

        RetrofitClient.api
            .listarEventos()
            .enqueue(object : Callback<List<Evento>> {

                override fun onResponse(
                    call: Call<List<Evento>>,
                    response: Response<List<Evento>>) {

                    if(response.isSuccessful){

                        val eventos =
                            response.body() ?: emptyList()

                        recycler.adapter = EventoAdapter(
                            lista = eventos,
                            onClick = { evento ->
                                val intent = Intent(
                                    this@EventosActivity,
                                    DetalhesActivity::class.java )

                                intent.putExtra("nome", evento.nome)
                                intent.putExtra("data", evento.data)
                                intent.putExtra("local", evento.local)
                                intent.putExtra("descricao", evento.descricao)
                                intent.putExtra("id", evento.id)
                                intent.putExtra("nomeAluno", nome)
                                intent.putExtra("isAdmin", false)

                                startActivity(intent)
                            }
                        )
                    }

                }

                override fun onFailure(
                    call: Call<List<Evento>>,
                    t: Throwable )
                {
                    t.printStackTrace()
                }
            } )

        val btnInscricoes =
            findViewById<Button>(
                R.id.btnMinhasInscricoes )

        btnInscricoes.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MinhasInscricoesActivity::class.java ) )
        }
    }
}