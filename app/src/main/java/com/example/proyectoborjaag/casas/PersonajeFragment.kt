package com.example.proyectoborjaag.casas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoborjaag.R


/**
 * Fragmento para mostrar la lista de personajes de una casa específica.
 */
class PersonajeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonajeAdapter

    companion object {
        private const val ARG_LAST_NAME = "last_name"

        /**
         * Crea una nueva instancia del fragmento para una casa específica.
         *
         * @param lastName El apellido de la casa.
         * @return Una instancia de `PersonajeFragment` configurada.
         */
        fun newInstance(lastName: String): PersonajeFragment {
            val fragment = PersonajeFragment()
            val args = Bundle()
            args.putString(ARG_LAST_NAME, lastName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personajes, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PersonajeAdapter()
        recyclerView.adapter = adapter

        selecionarPersonajes()
        return view
    }

    /**
     * Obtiene los personajes de la casa desde la API.
     */
    private fun selecionarPersonajes() {
        val lastName = arguments?.getString(ARG_LAST_NAME) ?: return
        val url = "https://thronesapi.com/api/v2/Characters"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val request = JsonArrayRequest(
            com.android.volley.Request.Method.GET, url, null,
            { response ->
                val characters = mutableListOf<Personaje>()
                for (i in 0 until response.length()) {
                    val characterJson = response.getJSONObject(i)
                    if (characterJson.getString("lastName").equals(lastName, ignoreCase = true)) {
                        characters.add(
                            Personaje(
                                id = characterJson.getInt("id"),
                                fullName = characterJson.getString("fullName"),
                                image = characterJson.getString("imageUrl")
                            )
                        )
                    }
                }
                adapter.submitList(characters)
            },
            { error ->
                Log.e("ERROR_API", "Error al seleccionar personajes", error)
            }
        )
        requestQueue.add(request)
    }
}
