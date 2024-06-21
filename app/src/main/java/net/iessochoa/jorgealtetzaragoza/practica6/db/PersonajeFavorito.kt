package net.iessochoa.jorgealtetzaragoza.practica6.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "personajes_favoritos")
data class PersonajeFavorito(
    @PrimaryKey val id: Int,
    val nombre: String,
    val especie: String,
    val imagenUrl: String,
    @TypeConverters(TransformaFechaSQLite::class)
    val created: Date
)