package net.iessochoa.jorgealtetzaragoza.practica6.model



import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Personaje(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String
) : Parcelable
