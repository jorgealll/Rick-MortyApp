package net.iessochoa.jorgealtetzaragoza.practica6.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonajeFavoritoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPersonajeFavorito(personajeFavorito: PersonajeFavorito)

    @Delete
    suspend fun deletePersonajeFavorito(personajeFavorito: PersonajeFavorito)

    @Query("SELECT * FROM personajes_favoritos")
    fun getAllPersonajesFavoritos(): LiveData<List<PersonajeFavorito>>

}
