package com.example.proyectoborjaag.citas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoborjaag.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

/**
 * Fragmento que muestra una lista de personajes y permite navegar a los detalles de un personaje.
 */
class CitasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView // RecyclerView para mostrar la lista de personajes.

    /**
     * Crea la vista del fragmento y configura el RecyclerView.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_citas, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchCharacters() // Obtiene los datos de los personajes desde la API.

        return view
    }

    /**
     * Realiza una solicitud HTTP para obtener los personajes desde la API.
     */
    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://api.gameofthronesquotes.xyz/v1/characters")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val characters = parseCharacters(response)

                    withContext(Dispatchers.Main) {
                        recyclerView.adapter = PersonajeDosAdapter(characters) { character ->
                            navigateToMuestraFragment(character)
                        }
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Analiza el JSON de la API y lo convierte en una lista de personajes.
     *
     * @param json La respuesta JSON de la API.
     * @return Una lista de objetos `PersonajeDos`.
     */
    private fun parseCharacters(json: String): List<PersonajeDos> {
        val jsonArray = JSONArray(json)
        val characters = mutableListOf<PersonajeDos>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val name = jsonObject.getString("name")

            val houseObject = jsonObject.optJSONObject("house")
            val houseName = houseObject?.optString("name") ?: "Casa desconocida"

            val quotesArray = jsonObject.getJSONArray("quotes")
            val quotes = mutableListOf<String>()

            for (j in 0 until quotesArray.length()) {
                quotes.add(quotesArray.getString(j))
            }

            characters.add(PersonajeDos(name, Casa(houseName), quotes))
        }
        return characters
    }

    /**
     * Navega al fragmento `MuestraFragment` pasando los detalles del personaje.
     *
     * @param character El personaje seleccionado.
     */
    private fun navigateToMuestraFragment(character: PersonajeDos) {
        val bundle = Bundle().apply {
            putString("name", character.name)
            putString("house", character.house?.name ?: "Casa desconocida")
            putStringArrayList("quotes", ArrayList(character.quotes))
        }
        findNavController().navigate(R.id.action_citasFragment_to_muestraFragment, bundle)
    }
}


