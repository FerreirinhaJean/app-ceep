package br.com.alura.ceep.webClient

import android.util.Log
import br.com.alura.ceep.model.Nota

class NotaWebClient {

    suspend fun getAll(): List<Nota>? {
        val listaResposta = AppRetrofit().notaService.getAllNotesCoroutines()
        val notas = listaResposta.body()?.map { notasRespostas ->
            notasRespostas.nota
        }
        return notas
    }

}