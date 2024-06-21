package net.iessochoa.jorgealtetzaragoza.practica6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.iessochoa.jorgealtetzaragoza.practica6.databinding.FragmentInfoBinding
import net.iessochoa.jorgealtetzaragoza.practica6.model.Personaje
import net.iessochoa.jorgealtetzaragoza.practica6.utils.cargaImagen

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personaje: Personaje? = arguments?.getParcelable("Personaje")
        personaje?.let { mostrarInformacion(it) }
    }

    private fun mostrarInformacion(personaje: Personaje) {
        binding.tvNombr.text = personaje.name
        binding.tvGenero.text = "Género: ${personaje.gender}"
        binding.tvEstado.text = "Estado: ${personaje.status}"
        binding.tvEspecie.text = "Tipo: ${personaje.species}"
        cargaImagen(binding.ivImagen2, personaje.image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}