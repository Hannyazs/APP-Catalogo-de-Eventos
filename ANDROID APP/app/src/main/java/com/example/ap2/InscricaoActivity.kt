package com.example.ap2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.ap2.api.RetrofitClient
import com.example.ap2.model.InscricaoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InscricaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscricao)

        val evento =
            intent.getStringExtra("evento")

        val eventoId =
            intent.getIntExtra("eventoId", 0)

        val nomeAluno =
            intent.getStringExtra("nomeAluno") ?: ""

        findViewById<TextView>(R.id.tvEvento)
            .text = "Evento: $evento"

        val check =
            findViewById<CheckBox>(R.id.checkAceito)

        findViewById<Button>(R.id.btnConfirmar)
            .setOnClickListener {

                if(check.isChecked){

                    val inscricao = InscricaoRequest(
                        nomeAluno = nomeAluno,
                        eventoId = eventoId
                    )

                    RetrofitClient.api
                        .criarInscricao(inscricao)
                        .enqueue(
                            object : Callback<Map<String, String>> {

                                override fun onResponse(
                                    call: Call<Map<String, String>>,
                                    response: Response<Map<String, String>> ) {

                                    Toast.makeText(
                                        this@InscricaoActivity,
                                        "Inscrição realizada!",
                                        Toast.LENGTH_LONG ).show()
                                }

                                override fun onFailure(
                                    call: Call<Map<String, String>>,
                                    t: Throwable ) {

                                    Toast.makeText(
                                        this@InscricaoActivity,
                                        "Erro: ${t.message}",
                                        Toast.LENGTH_LONG ).show()
                                }
                            } )

                } else {

                    Toast.makeText(
                        this,
                        "Confirme a participação",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}