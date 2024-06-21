package net.iessochoa.jorgealtetzaragoza.practica6.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import net.iessochoa.jorgealtetzaragoza.practica6.R

fun cargaImagen(ivImagen: ImageView, uri:String) {
    Glide
        .with(ivImagen.getContext())
        //url de la imagen
        .load(uri)
        //centramos la imagen
        // .centerCrop()
        //mientras se carga la imagen que imagen queremos mostrar
        //crea los iconos de placeholder y error
        .placeholder(R.drawable.descargando)
        //Imagen que mostramos si hay error
        .error(R.drawable.error)
        //donde colocamos la imagen
        .into(ivImagen);
}