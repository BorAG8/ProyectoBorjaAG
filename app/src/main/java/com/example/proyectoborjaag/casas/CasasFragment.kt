package com.example.proyectoborjaag.casas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoborjaag.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Fragmento que gestiona el TabLayout y el ViewPager2 para mostrar las casas principales.
 */
class CasasFragment : Fragment() {

    private lateinit var tabLayout: TabLayout // Componente para las pestañas.
    private lateinit var viewPager: ViewPager2 // Componente para las páginas.
    private lateinit var adapter: CasasAdapter // Adaptador para el ViewPager2.

    /**
     * Configura la vista del fragmento.
     *
     * @param inflater Inflador para cargar el layout.
     * @param container Contenedor padre donde se mostrará el fragmento.
     * @param savedInstanceState Estado previamente guardado (si existe).
     * @return La vista configurada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_casas, container, false)

        // Inicializa los componentes del layout.
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        // Obtiene los apellidos de las casas desde la API.
        seleccionarLastNames { lastNames ->
            Log.d("LAST_NAMES", "Fetched last names: $lastNames")

            // Configura el adaptador para el ViewPager2.
            adapter = CasasAdapter(this, lastNames)
            viewPager.adapter = adapter

            // Conecta el TabLayout con el ViewPager2.
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                // Configura la vista personalizada para cada pestaña.
                val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab, null)
                val tabTextView = tabView.findViewById<TextView>(R.id.tabText)
                val tabImageView = tabView.findViewById<ImageView>(R.id.tabIcon)

                // Configura el texto de la pestaña.
                tabTextView.text = lastNames[position]

                // Configura el escudo de la casa.
                val escudosCasas = mapOf(
                    "Targaryen" to R.drawable.targaryen,
                    "Lannister" to R.drawable.lannister,
                    "Baratheon" to R.drawable.baratheon,
                    "Stark" to R.drawable.stark,
                    "Greyjoy" to R.drawable.greyjoy
                )
                tabImageView.setImageResource(escudosCasas[lastNames[position]] ?: R.drawable.placeholder)

                tab.customView = tabView
            }.attach()
        }

        return view
    }

    /**
     * Obtiene los apellidos de las casas principales desde la API.
     *
     * @param callback Función a ejecutar con los apellidos de las casas como parámetro.
     */
    private fun seleccionarLastNames(callback: (List<String>) -> Unit) {
        val url = "https://thronesapi.com/api/v2/Characters"
        val requestQueue = Volley.newRequestQueue(requireContext())

        // Casas principales que queremos mostrar.
        val mainHouses = setOf("Targaryen", "Lannister", "Baratheon", "Stark", "Greyjoy")

        // Realiza la solicitud a la API.
        val request = JsonArrayRequest(
            com.android.volley.Request.Method.GET, url, null,
            { response ->
                val lastNames = mutableSetOf<String>()
                for (i in 0 until response.length()) {
                    val personajeJson = response.getJSONObject(i)
                    val lastName = personajeJson.getString("lastName").takeIf { it.isNotEmpty() } ?: "Unknown"

                    // Agrega el apellido si pertenece a una casa principal.
                    if (mainHouses.contains(lastName)) {
                        lastNames.add(lastName)
                    }
                }
                Log.d("LAST_NAMES_LIST", "Filtered list of last names: ${lastNames.toList()}")
                callback(lastNames.toList().sorted())
            },
            { error ->
                Log.e("ERROR_API", "Error seleccionando los last names", error)
            }
        )
        requestQueue.add(request)
    }
}

