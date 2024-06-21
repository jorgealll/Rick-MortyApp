package net.iessochoa.jorgealtetzaragoza.practica6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import net.iessochoa.jorgealtetzaragoza.practica6.databinding.FragmentInfoBinding
import net.iessochoa.jorgealtetzaragoza.practica6.model.Personaje
import net.iessochoa.jorgealtetzaragoza.practica6.utils.cargaImagen

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [info.newInstance] factory method to
 * create an instance of this fragment.
 */
class info : Fragment() {
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
        setHasOptionsMenu(true)
        val personaje: Personaje? = arguments?.getParcelable("Personaje")
        personaje?.let { mostrarInformacion(it) }
    }

    private fun mostrarInformacion(personaje: Personaje) {
        binding.tvNombr.text = personaje.name
        binding.tvGenero.text = personaje.gender
        binding.tvEstado.text = personaje.status
        binding.tvEspecie.text = personaje.species
        cargaImagen(binding.ivImagen2, personaje.image)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}