package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webClient.NotaWebClient
import kotlinx.coroutines.flow.Flow

class NotaRepository(
    private val dao: NotaDao,
    private  val webClient: NotaWebClient
) {

    fun getAll(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    suspend fun updateAll(){
        webClient.getAll()?.let {notas ->
            dao.salva(notas)
        }
    }

}