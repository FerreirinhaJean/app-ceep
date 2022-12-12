package br.com.alura.ceep.webClient.service

import br.com.alura.ceep.webClient.model.NotaResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NotaService {

    @GET("notas")
    fun getAllNotes(): Call<List<NotaResponse>>

    @GET("notas")
    suspend fun getAllNotesCoroutines(): Response<List<NotaResponse>>

}