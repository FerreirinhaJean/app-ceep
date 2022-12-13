package br.com.alura.ceep.webClient

import android.util.Log
import br.com.alura.ceep.model.Nota

class NotaWebClient {

    suspend fun getAll(): List<Nota>? {
        return try {
            val listaResposta = AppRetrofit().notaService.getAllNotesCoroutines()
            listaResposta.body()?.map { notasRespostas -> notasRespostas.nota }
        } catch (e: Exception) {
            null
        }
    }

}