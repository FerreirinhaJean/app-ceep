package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityListaNotasBinding
import br.com.alura.ceep.extensions.vaiPara
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.alura.ceep.webClient.AppRetrofit
import br.com.alura.ceep.webClient.NotaWebClient
import br.com.alura.ceep.webClient.model.NotaResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaNotasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaNotasBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }
    private val dao by lazy {
        AppDatabase.instancia(this).notaDao()
    }
    private val notaWebCliente by lazy {
        NotaWebClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
        lifecycleScope.launch {
            val notas = notaWebCliente.getAll()
            Log.i("ListaNotasActivity", "onCreate: ${notas}")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscaNotas()
            }
        }
//        retrofitSemCoroutine()

    }

    private fun retrofitSemCoroutine() {
        val call = AppRetrofit().notaService.getAllNotes()
        call.enqueue(object : Callback<List<NotaResponse>?> {
            override fun onResponse(
                call: Call<List<NotaResponse>?>,
                response: Response<List<NotaResponse>?>
            ) {
                response.body()?.let { notasResponse ->
                    val notas = notasResponse.map { it.nota }
                    Log.i("ListaNotasActivity", "onResponse: ${notas}")
                }
            }

            override fun onFailure(call: Call<List<NotaResponse>?>, t: Throwable) {
                Log.e("ListaNotasActivity", "onFailure: ", t)
            }
        })
    }

    private fun configuraFab() {
        binding.activityListaNotasFab.setOnClickListener {
            Intent(this, FormNotaActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun configuraRecyclerView() {
        binding.activityListaNotasRecyclerview.adapter = adapter
        adapter.quandoClicaNoItem = { nota ->
            vaiPara(FormNotaActivity::class.java) {
                putExtra(NOTA_ID, nota.id)
            }
        }
    }

    private suspend fun buscaNotas() {
        dao.buscaTodas()
            .collect { notasEncontradas ->
                binding.activityListaNotasMensagemSemNotas.visibility =
                    if (notasEncontradas.isEmpty()) {
                        binding.activityListaNotasRecyclerview.visibility = GONE
                        VISIBLE
                    } else {
                        binding.activityListaNotasRecyclerview.visibility = VISIBLE
                        adapter.atualiza(notasEncontradas)
                        GONE
                    }
            }
    }
}