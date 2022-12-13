package br.com.alura.ceep.webClient.model

data class NotaRequest(
    val titulo: String,
    val descricao: String,
    val imagem: String? = null
) {
}