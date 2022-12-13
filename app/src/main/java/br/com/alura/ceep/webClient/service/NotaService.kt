package br.com.alura.ceep.webClient.service

import br.com.alura.ceep.webClient.model.NotaRequest
import br.com.alura.ceep.webClient.model.NotaResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotaService {

    @GET("notas")
    fun getAllNotes(): Call<List<NotaResponse>>

    @GET("notas")
    suspend fun getAllNotesCoroutines(): Response<List<NotaResponse>>

    @PUT("notas/{id}")
    suspend fun salva(
        @Path("id") id: String,
        @Body nota: NotaRequest
    ): Response<NotaResponse>

}