package com.example.ap2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2.adapter.InscricaoAdapter
import com.example.ap2.api.RetrofitClient
import com.example.ap2.model.Inscricao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MinhasInscricoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_minhas_inscricoes
        )

        val recycler =
            findViewById<RecyclerView>(
                R.id.recyclerInscricoes
            )

        recycler.layoutManager =
            LinearLayoutManager(this)

        RetrofitClient.api
            .listarInscricoes()
            .enqueue(
                object : Callback<List<Inscricao>> {

                    override fun onResponse(
                        call: Call<List<Inscricao>>,
                        response: Response<List<Inscricao>>
                    ) {

                        if(response.isSuccessful){

                            val lista =
                                response.body()
                                    ?: emptyList()

                            recycler.adapter =
                                InscricaoAdapter(lista)
                        }
                    }

                    override fun onFailure(
                        call: Call<List<Inscricao>>,
                        t: Throwable
                    ) {

                        t.printStackTrace()
                    }
                }
            )
    }
}