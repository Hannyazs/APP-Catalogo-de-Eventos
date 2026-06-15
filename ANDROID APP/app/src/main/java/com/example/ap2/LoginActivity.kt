package com.example.ap2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val spinner =
            findViewById<Spinner>(R.id.spTipoUsuario)

        val tipos =
            arrayOf(
                "Aluno",
                "Admin")

        spinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                tipos)

        val etNome = findViewById<EditText>(R.id.etNome)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val nome = etNome.text.toString()

        btnEntrar.setOnClickListener {

            val nome =
                etNome.text.toString()

            val tipo =
                spinner.selectedItem.toString()

            if(nome.isEmpty()){

                Toast.makeText(
                    this,
                    "Digite seu nome",
                    Toast.LENGTH_SHORT ).show()

                return@setOnClickListener
            }

            if(tipo == "Admin"){

                val intent =
                    Intent(
                        this,
                        EventosAdminActivity::class.java )

                intent.putExtra(
                    "nome",
                    nome )

                intent.putExtra(
                    "isAdmin",
                    true )

                startActivity(intent)

            }else{

                val intent =
                    Intent(
                        this,
                        EventosActivity::class.java )

                intent.putExtra(
                    "nome",
                    nome )

                intent.putExtra(
                    "isAdmin",
                    false )

                startActivity(intent)
            }
        }

    }
}