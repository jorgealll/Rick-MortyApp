package net.iessochoa.jorgealtetzaragoza.practica6.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PersonajeFavorito::class], version = 1, exportSchema = false)
@TypeConverters(TransformaFechaSQLite::class)
abstract class PersonajesDataBase : RoomDatabase() {

    abstract fun personajeFavoritoDao(): PersonajeFavoritoDao

    companion object {
        private var INSTANCE: PersonajesDataBase? = null

        fun getInstance(context: Context): PersonajesDataBase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PersonajesDataBase::class.java,
                    "personajes_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
