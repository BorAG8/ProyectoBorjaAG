package com.example.proyectoborjaag.ubis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoborjaag.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Fragmento que muestra una lista de ubicaciones con imágenes, nombres y casillas de verificación.
 */
class UbicacionesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView // RecyclerView para mostrar las ubicaciones.
    private lateinit var adapter: ImagenAdapter // Adaptador para gestionar los datos de las ubicaciones.
    private val items = mutableListOf<ItemImagen>() // Lista de elementos que se mostrarán en el RecyclerView.

    /**
     * Configura las vistas y la lógica del fragmento después de que la vista ha sido creada.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController() // Controlador de navegación.

        // Lista de imágenes y nombres de las ubicaciones.
        val imageIds = listOf(
            R.drawable.almodovardelrio, R.drawable.bardenasreales, R.drawable.bermeo,
            R.drawable.cabodegata, R.drawable.castillodesantaflorencia, R.drawable.castillodezafra, R.drawable.laalcazaba,
            R.drawable.peniscola, R.drawable.santiponce, R.drawable.zumaia
        )
        val names = listOf(
            "Almodovar Del Rio", "Bardenas Reales", "Bermeo", "Cabo De Gata",
            "Castillo De Santa Florencia", "Castillo De Zafra", "La Alcazaba",
            "Peñiscola", "Santiponce", "Zumaia"
        )

        // Crea una lista de `ItemImagen` combinando las imágenes y nombres.
        items.addAll(imageIds.zip(names).map { ItemImagen(it.first, it.second) })

        // Configura el RecyclerView.
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ImagenAdapter(items) { position, isChecked ->
            // Actualiza el estado de la casilla de verificación en la lista.
            items[position] = items[position].copy(isChecked = isChecked)
        }
        recyclerView.adapter = adapter

        // Configura el FloatingActionButton para mostrar un mensaje con las ubicaciones seleccionadas.
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val visitedPlaces = items.filter { it.isChecked }.joinToString(", ") { it.name }

            if (visitedPlaces.isEmpty()) {
                // Muestra un mensaje si no se seleccionaron ubicaciones.
                Toast.makeText(requireContext(), "Has viajado a alguno de estos lugares?", Toast.LENGTH_LONG).show()
            } else {
                // Muestra las ubicaciones seleccionadas.
                Toast.makeText(requireContext(), "Has visitado: $visitedPlaces", Toast.LENGTH_LONG).show()
            }
        }

        // Configura la barra de navegación inferior para manejar la navegación.
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    navController.navigate(R.id.principalFragment)
                    true
                }
                R.id.action_citas -> {
                    navController.navigate(R.id.citasFragment)
                    true
                }
                R.id.action_casas -> {
                    navController.navigate(R.id.personajesFragment)
                    true
                }
                R.id.action_episodios -> {
                    navController.navigate(R.id.episodiosFragment)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Infla el layout asociado al fragmento.
     *
     * @param inflater Inflador para el layout.
     * @param container Contenedor padre del fragmento.
     * @param savedInstanceState Estado previamente guardado (si existe).
     * @return La vista configurada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ubicaciones, container, false)
    }
}
