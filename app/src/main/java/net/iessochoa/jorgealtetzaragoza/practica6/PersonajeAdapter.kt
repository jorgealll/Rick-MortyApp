import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.iessochoa.jorgealtetzaragoza.practica6.R
import net.iessochoa.jorgealtetzaragoza.practica6.model.Personaje


class PersonajeAdapter(
    private var personajes: List<Personaje>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(personaje: Personaje)
    }

    inner class PersonajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nombreTextView: TextView = itemView.findViewById(R.id.tvNombre)
        val descripcionTextView: TextView = itemView.findViewById(R.id.tvDescripcion)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivImagen)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemClick(personajes[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items, parent, false)
        return PersonajeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val personaje = personajes[position]
        holder.nombreTextView.text = personaje.name
        holder.descripcionTextView.text = personaje.species

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(personaje)
        }

        cargaImagen(holder.ivAvatar, personaje.image)
    }

    override fun getItemCount() = personajes.size

    fun cargaImagen(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.descargando)
            .error(R.drawable.error)
            .into(imageView)
    }

    fun setPersonajes(newPersonajes: List<Personaje>) {
        personajes = newPersonajes
        notifyDataSetChanged()
    }
}
