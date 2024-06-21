package net.iessochoa.jorgealtetzaragoza.practica6.model

import com.google.gson.annotations.SerializedName

data class RespuestaRickMorty(
    val info: Info,
    @SerializedName("results")
    val listaPersonajes: List<Personaje>

)