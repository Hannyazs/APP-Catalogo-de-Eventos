package com.example.ap2.api

import com.example.ap2.model.Evento
import com.example.ap2.model.EventoRequest
import retrofit2.Call
import retrofit2.http.GET
import com.example.ap2.model.Inscricao
import com.example.ap2.model.InscricaoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("eventos")
    fun listarEventos(): Call<List<Evento>>

    @POST("inscricoes")
    fun criarInscricao(
        @Body inscricao: InscricaoRequest
    ): Call<Map<String, String>>
    @GET("inscricoes")
    fun listarInscricoes(): Call<List<Inscricao>>

    @POST("eventos")
    fun criarEvento(
        @Body evento: EventoRequest
    ): Call<Map<String, String>>

    @PUT("eventos/{id}")
    fun atualizarEvento(
        @Path("id") id: Int,
        @Body evento: EventoRequest
    ): Call<Map<String, String>>

    @DELETE("eventos/{id}")
    fun deletarEvento(
        @Path("id") id: Int
    ): Call<Map<String, String>>

}