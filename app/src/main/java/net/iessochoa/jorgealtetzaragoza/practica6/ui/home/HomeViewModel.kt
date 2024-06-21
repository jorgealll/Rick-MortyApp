package net.iessochoa.jorgealtetzaragoza.practica6.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.iessochoa.jorgealtetzaragoza.practica6.model.Personaje
import net.iessochoa.jorgealtetzaragoza.practica6.repository.Repository

class HomeViewModel (application: Application):
    AndroidViewModel(application) {
    val personajesLiveData:LiveData<MutableList<Personaje>>
    val estadoServicioLiveData by lazy { Repository.getEstadoServicioLiveData() }
    init {
        //iniciamos el repositorio
        Repository(application)
        //observaremos los cambios en la lista de personajes
        personajesLiveData=Repository.getLiveDataListaPersonajes()
        //carga la primera página de personajes
        getNextPersonajes()
    }
    //lanza en segundo plano la carga de nuevos personajes
    fun getNextPersonajes()=viewModelScope.launch(Dispatchers.IO) {
        Repository.getNextPersonajes()
    }
}