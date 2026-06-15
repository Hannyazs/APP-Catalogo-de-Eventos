package com.example.ap2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ap2.api.RetrofitClient
import com.example.ap2.model.EventoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class CadastroEventoActivity : AppCompatActivity() {

    private var eventoId: Int = -1 // Variável de controle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_evento)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etData = findViewById<EditText>(R.id.etData)
        val etHorario = findViewById<EditText>(R.id.etHorario)
        val etLocal = findViewById<EditText>(R.id.etLocal)
        val etDescricao = findViewById<EditText>(R.id.etDescricao)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        // CONFIGURAÇÃO DOS SELETORES (PICKERS)
        etData.isFocusable = false
        etData.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                etData.setText(date)
            }, year, month, day).show()
        }

        etHorario.isFocusable = false
        etHorario.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                etHorario.setText(time)
            }, hour, minute, true).show()
        }

        // VERIFICA SE VEIO UM ID PARA EDIÇÃO
        eventoId = intent.getIntExtra("id", -1)

        if (eventoId != -1) {
            // Se tem ID, preenche os campos com os dados antigos
            etNome.setText(intent.getStringExtra("nome"))
            etData.setText(intent.getStringExtra("data"))
            etHorario.setText(intent.getStringExtra("horario"))
            etLocal.setText(intent.getStringExtra("local"))
            etDescricao.setText(intent.getStringExtra("descricao"))
            btnSalvar.text = "Atualizar Evento" // Muda o texto do botão
        }

        btnSalvar.setOnClickListener {
            val evento = EventoRequest(
                nome = etNome.text.toString(),
                data = etData.text.toString(),
                horario = etHorario.text.toString(),
                local = etLocal.text.toString(),
                descricao = etDescricao.text.toString()
            )

            if (eventoId != -1) {
                // SE TEM ID, FAZ UM PUT (EDITAR)
                RetrofitClient.api.atualizarEvento(eventoId, evento)
                    .enqueue(object : Callback<Map<String, String>> {
                        override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                            Toast.makeText(this@CadastroEventoActivity, "Evento atualizado!", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                            Toast.makeText(this@CadastroEventoActivity, "Erro: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
            } else {
                // SE NÃO TEM ID, FAZ UM POST (CRIAR NOVO)
                RetrofitClient.api.criarEvento(evento)
                    .enqueue(object : Callback<Map<String, String>> {
                        override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                            Toast.makeText(this@CadastroEventoActivity, "Evento criado!", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                            Toast.makeText(this@CadastroEventoActivity, "Erro: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
    }
}