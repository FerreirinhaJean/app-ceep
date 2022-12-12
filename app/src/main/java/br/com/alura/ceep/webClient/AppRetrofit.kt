package br.com.alura.ceep.webClient

import br.com.alura.ceep.webClient.service.NotaService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppRetrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.112:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val notaService = retrofit.create(NotaService::class.java)

}