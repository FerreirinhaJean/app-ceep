package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webClient.NotaWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotaRepository(
    private val dao: NotaDao,
    private val webClient: NotaWebClient
) {

    fun getAll(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    private suspend fun updateAll() {
        webClient.getAll()?.let { notas ->
            val notasSincronizadas = notas.map {
                it.copy(sincronizado = true)
            }
            dao.salva(notasSincronizadas)
        }
    }

    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(id: String) {
        dao.remove(id)
    }

    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        if (webClient.salva(nota)) {
            val notaSincronizada = nota.copy(sincronizado = true)
            dao.salva(notaSincronizada)
        }
    }

    suspend fun sincroniza() {
        val notasNaoSincronizadas = dao.buscaNaoSincronizadas().first()
        notasNaoSincronizadas.forEach { notaNaoSincronizada ->
            salva(notaNaoSincronizada)
        }
        updateAll()
    }


}