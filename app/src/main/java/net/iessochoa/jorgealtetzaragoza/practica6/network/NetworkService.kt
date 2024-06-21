package net.iessochoa.jorgealtetzaragoza.practica6.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import net.iessochoa.jorgealtetzaragoza.practica6.model.Personaje
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.NetworkInterface

object NetworkService {
    val TAG = "Practica6 "
    private lateinit var application: Application
    operator fun invoke(context: Context) {
        //necesitaremos el context para saber si tenemos conexión
        this.application = context.applicationContext as Application
    }
    //Base de la url de la Api
    //IMPORTANTE: la baseURL siempre tiene que terminar con una barra
    private val URI_BASE = "https://rickandmortyapi.com/api/"
    //lista de Personajes. Inicialmente 0
    private val listaPersonajes: MutableList<Personaje> =
        mutableListOf()
    //livedata que observaremos en la vista
    private val personajesLiveData = MutableLiveData(listaPersonajes)
    //página que estamos leyendo actualmente. Iremos trayendo nuevas
    //y las añadiremos a la lista
    //recuerda el formato
    private var pagina = 1
    //creamos el conversor de String a fecha de JSON. Lo hará

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()
    //iniciamos retrofit con los métodos de acceso
    private val servicioRickMorty = Retrofit.Builder()
        .baseUrl(URI_BASE)
        ///conversor de JSON, existen otros(XML,csv...consultar ayuda)
        .addConverterFactory(GsonConverterFactory.create(gson))
        //Crea el servicio con los métodos definidos de la interface
        .build().create(RickMortyService::class.java)
    /**
     * Este método se conectará al servicio y pedirá las siguientes
    página de la colección de
     * personajes. Es necesario tener conexión a Internet y ejecutarse
    en segundo plano
     */
    suspend fun getNextPersonajes() {
        //comprobamos si tenemos Internet(la función está más abajo)
        if (isConnected(application)) {
            try {
                //Llamamos al servicio y esperamos la llegada de datos
                //Pedimos la página, pero podíamos haber utilizado la
                //'next' de la clase 'info'
                val respuesta =
                    servicioRickMorty.listaPersonajes(pagina)
                if (respuesta.isSuccessful) {//si es correcto
                    //leemos el objeto de tipo RespuestaRickMorty
                    val respuestaRickMorty = respuesta.body()
                    //recordad que tiene dos campos: info y listaPersonajes
                    val listado = respuestaRickMorty?.listaPersonajes
                    if (listado != null) {
                        //añadimos a la lista los nuevos personajes
                        listaPersonajes.addAll(listado)
                        Log.i(TAG, "Personajes actuales: ${listaPersonajes.size}")
                        Log.i(
                            TAG,
                            "Siguiente Página:${respuestaRickMorty.info.next?.substringAfterLast("=") ?: "ninguno"}"
                        )

                        //actualizamos el LiveData
                        personajesLiveData.postValue(listaPersonajes)

                        // Verificamos si hay más páginas para leer
                        if (pagina <= respuestaRickMorty.info.pages) {
                            // siguiente página para la siguiente lectura
                            pagina++
                        } else {
                            Log.i(TAG, "Se han leído todas las páginas disponibles.")
                        }
                    } else {
                        Log.e(
                            TAG,
                            "error en el acceso al servicio: $(respuesta.errorBody().toString())"
                        )
                        estadoServicioLiveData.postValue(EstadoServicio.ERROR)
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "error en el acceso al servicio: $(respuesta.errorBody().toString())")
                estadoServicioLiveData.postValue(EstadoServicio.ERROR)

            }
        } else {
            Log.e(TAG, "error de acceso a Internet")
            estadoServicioLiveData.postValue(EstadoServicio.ERROR)

        }
    }

    /**
     * Nos devuelve el LiveData con la lista
     */
    fun getLiveDataListaPersonajes ():
            LiveData<MutableList<Personaje>> {
        return personajesLiveData
    }
    /**
     * Función genérica que permite saber si tenemos conexión a
    internet
     */
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                    ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =

                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if
                        (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i(TAG,
                        "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if
                               (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if
                               (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i(TAG,
                        "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
    //LiveData para controlar el icono de progreso en la vista
    enum class EstadoServicio { LEYENDO, PARADO, ERROR }
    private val estadoServicioLiveData =
        MutableLiveData<EstadoServicio>(EstadoServicio.PARADO)

    fun geteEstadoServicioLiveData(): LiveData<EstadoServicio>{
        return estadoServicioLiveData
    }
}