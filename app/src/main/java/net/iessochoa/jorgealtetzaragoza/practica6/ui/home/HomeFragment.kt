package net.iessochoa.jorgealtetzaragoza.practica6.ui.home

import PersonajeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.jorgealtetzaragoza.practica6.databinding.FragmentHomeBinding
import net.iessochoa.jorgealtetzaragoza.practica6.model.Personaje
import net.iessochoa.jorgealtetzaragoza.practica6.network.NetworkService
import net.iessochoa.jorgealtetzaragoza.practica6.utils.cargaImagen
import androidx.navigation.fragment.findNavController
import net.iessochoa.jorgealtetzaragoza.practica6.R

class HomeFragment : Fragment(), PersonajeAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val personajesAdapter = PersonajeAdapter(emptyList(), this)

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvItems.layoutManager = LinearLayoutManager(context)
        binding.rvItems.adapter = personajesAdapter

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.personajesLiveData.observe(viewLifecycleOwner) { personajes ->
            personajesAdapter.setPersonajes(personajes)
        }

        defineDetectarFinRecycler()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaBarraProgreso()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun defineDetectarFinRecycler() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(-1) && !recyclerView.canScrollVertically(1)) {
                    homeViewModel.getNextPersonajes()
                }
            }
        }
        binding.rvItems.addOnScrollListener(scrollListener)
    }
    private fun iniciaBarraProgreso(){
        homeViewModel.estadoServicioLiveData
            .observe(viewLifecycleOwner){
                binding.pbLeyendoPersonajes.visibility= when(it){
                    NetworkService.EstadoServicio.LEYENDO ->View.VISIBLE
                    NetworkService.EstadoServicio.PARADO ->View.INVISIBLE
                    NetworkService.EstadoServicio.ERROR->{
                        Toast.makeText(requireContext(),"Problemas para acceder al servivio",Toast.LENGTH_SHORT).show()
                        View.INVISIBLE
                    }
                }
                if(it== NetworkService.EstadoServicio.LEYENDO){
                    binding.pbLeyendoPersonajes.visibility=View.VISIBLE
                }
            }
    }
    override fun onItemClick(personaje: Personaje) {
        val bundle = Bundle()
        bundle.putParcelable("Personaje", personaje)
        findNavController().navigate(R.id.action_homeFragment_to_infoFragment, bundle)
    }


}