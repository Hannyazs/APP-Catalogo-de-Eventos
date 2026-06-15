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

class EventosAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eventos_admin)

        val nome = intent.getStringExtra("nome") ?: "Admin"

        val tvBoasVindas = findViewById<TextView>(R.id.tvBoasVindas)
        tvBoasVindas.text = "Administrador: $nome"

        val recycler = findViewById<RecyclerView>(R.id.recyclerEventos)
        recycler.layoutManager = LinearLayoutManager(this)

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
                                // Vai para detalhes (como já estava)
                                val intent = Intent(this@EventosAdminActivity, DetalhesActivity::class.java)
                                intent.putExtra("nome", evento.nome)
                                intent.putExtra("data", evento.data)
                                intent.putExtra("local", evento.local)
                                intent.putExtra("descricao", evento.descricao)
                                intent.putExtra("id", evento.id)
                                intent.putExtra("nomeAluno", nome)
                                intent.putExtra("isAdmin", true)
                                startActivity(intent)
                            },
                            onEditClick = { evento ->
                                // Abre a tela de Cadastro passando os dados do evento
                                val intent = Intent(this@EventosAdminActivity, CadastroEventoActivity::class.java)
                                intent.putExtra("id", evento.id)
                                intent.putExtra("nome", evento.nome)
                                intent.putExtra("data", evento.data)
                                intent.putExtra("horario", evento.horario)
                                intent.putExtra("local", evento.local)
                                intent.putExtra("descricao", evento.descricao)
                                startActivity(intent)
                            },
                            onDeleteClick = { evento ->
                                // Faz a chamada para a API para deletar
                                RetrofitClient.api.deletarEvento(evento.id).enqueue(object : Callback<Map<String, String>> {
                                    override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                                        if (response.isSuccessful) {
                                            // Atualiza a lista chamando a API novamente (ou removendo da lista local)
                                            recreate() // Jeito rápido de recarregar a tela atualizando a lista
                                        }
                                    }
                                    override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                                        t.printStackTrace()
                                    }
                                })
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

        val btnNovoEvento =
            findViewById<Button>(R.id.btnNovoEvento)

        btnNovoEvento.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CadastroEventoActivity::class.java ) )
        }
    }
}