package br.com.alura.ceep.webClient

import android.util.Log
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webClient.model.NotaRequest

class NotaWebClient {

    private val notaService = AppRetrofit().notaService

    suspend fun getAll(): List<Nota>? {
        return try {
            val listaResposta = notaService.getAllNotesCoroutines()
            listaResposta.body()?.map { notasRespostas -> notasRespostas.nota }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun salva(nota: Nota): Boolean {
        try {
            val response = notaService.salva(
                nota.id,
                NotaRequest(
                    titulo = nota.titulo,
                    descricao = nota.descricao,
                    imagem = nota.imagem
                )
            )

            return response.isSuccessful
        } catch (e: Exception) {
            Log.e("NotaWebClient", "salva: falha ao tentar salvar")
            return false
        }
    }

}