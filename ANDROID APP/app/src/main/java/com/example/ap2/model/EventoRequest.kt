package com.example.ap2.model

data class EventoRequest(
    val nome: String,
    val data: String,
    val horario: String,
    val local: String,
    val descricao: String
)