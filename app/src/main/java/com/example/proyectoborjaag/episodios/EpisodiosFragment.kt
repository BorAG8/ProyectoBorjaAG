package com.example.proyectoborjaag.episodios

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoborjaag.R

/**
 * Representa un episodio con su nombre y calificación.
 *
 * @property name Nombre del episodio.
 * @property rating Calificación asignada al episodio (por defecto 0).
 */
data class Episode(val name: String, var rating: Int)

/**
 * Fragmento que muestra una lista de episodios organizados por temporada, permitiendo asignar calificaciones.
 */
class EpisodiosFragment : Fragment() {

    // RecyclerView y su adaptador para mostrar la lista de episodios.
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EpisodiosAdapter

    // RatingBar para seleccionar la puntuación del episodio.
    private lateinit var ratingBar: RatingBar

    // Spinner para seleccionar la temporada.
    private lateinit var spinner: Spinner

    // SharedPreferences para guardar las calificaciones de los episodios.
    private lateinit var sharedPreferences: SharedPreferences

    // Datos de los episodios organizados por temporada.
    private val episodesPerSeason = mapOf(
        1 to listOf(
            "Winter Is Coming",
            "El Camino Real",
            "Lord Nieve",
            "Cripples, Bastards, and Broken Things",
            "El lobo y el león",
            "Una corona de oro",
            "Ganas o mueres",
            "Por el lado de la punta",
            "Baelor",
            "Fire and Blood"
        ),
        2 to listOf(
            "The North Remembers",
            "The Night Lands",
            "What Is Dead May Never Die",
            "Garden of Bones",
            "The Ghost of Harrenhal",
            "The Old Gods and the New",
            "A Man Without Honor",
            "The Prince of Winterfell",
            "Blackwater",
            "Valar Morghulis"
        ),
        3 to listOf(
            "Valar Dohaeris",
            "Dark Wings, Dark Words",
            "Walk of Punishment",
            "And Now His Watch is Ended",
            "Kissed by Fire",
            "The Climb",
            "The Bear and the Maiden Fair",
            "Second Sons",
            "The Rains of Castamere",
            "Mhysa"
        ),
        4 to listOf(
            "Two Swords",
            "The Lion and the Rose",
            "Breaker of Chains",
            "Oathkeeper",
            "First of His Name",
            "The Laws of Gods and Men",
            "Mockingbird",
            "The Mountain and the Viper",
            "The Watchers on the Wall",
            "The Children"
        ),
        5 to listOf(
            "The Wars to Come", "The House of Black and White", "High Sparrow", "Sons of the Harpy",
            "Kill the Boy", "Unbowed, Unbent, Unbroken", "The Gift", "Hardhome",
            "The Dance of Dragons", "Mother's Mercy"
        ),
        6 to listOf(
            "The Red Woman",
            "Home",
            "Oathbreaker",
            "Book of the Stranger",
            "The Door",
            "Blood of My Blood",
            "The Broken Man",
            "No One",
            "Battle of the Bastards",
            "The Winds of Winter"
        ),
        7 to listOf(
            "Dragonstone", "Nacida de la Tormenta", "The Queen's Justice", "The Spoils of War",
            "Eastwatch", "Beyond the Wall", "The Dragon and the Wolf"
        ),
        8 to listOf(
            "Winterfell",
            "A Knight of the Seven Kingdoms",
            "The Long Night",
            "The Last of the Starks",
            "The Bells",
            "The Iron Throne"
        )
    )

    // Temporada seleccionada por el usuario, inicialmente 1.
    private var selectedSeason = 1

    // Lista actual de episodios que se muestran en la UI.
    private val episodes = mutableListOf<Episode>()

    /**
     * Infla el layout del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episodios, container, false)
    }

    /**
     * Configura las vistas y la lógica del fragmento después de que la vista ha sido creada.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa SharedPreferences para guardar calificaciones.
        sharedPreferences =
            requireContext().getSharedPreferences("episode_ratings", Context.MODE_PRIVATE)

        // Referencias a las vistas.
        ratingBar = view.findViewById(R.id.ratingBar)
        spinner = view.findViewById(R.id.spinner_temporadas)
        recyclerView = view.findViewById(R.id.recyclerView)

        // Configura el RecyclerView con un layout manager y el adaptador.
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EpisodiosAdapter(episodes) { position ->
            val selectedRating = ratingBar.rating.toInt() // Obtiene la puntuación del RatingBar.
            episodes[position].rating = selectedRating // Actualiza la calificación del episodio.
            adapter.notifyItemChanged(position) // Notifica cambios en el adaptador.

            // Guarda la puntuación en SharedPreferences.
            val editor = sharedPreferences.edit()
            val key = "season_${selectedSeason}_episode_$position"
            editor.putInt(key, selectedRating)
            editor.apply()
        }
        recyclerView.adapter = adapter

        // Configura el Spinner para mostrar las temporadas.
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            (1..8).map { "Temporada $it" }
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        // Maneja la selección de una temporada en el Spinner.
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSeason = position + 1 // Actualiza la temporada seleccionada.
                updateEpisodes() // Actualiza los episodios mostrados.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        updateEpisodes() // Inicializa la lista de episodios para la temporada 1.

        // Configura el botón para reiniciar calificaciones.
        val resetButton = view.findViewById<Button>(R.id.btn_reset_ratings)
        resetButton.setOnClickListener {
            resetRatings()
        }
    }

    /**
     * Actualiza la lista de episodios para la temporada seleccionada.
     */
    private fun updateEpisodes() {
        episodes.clear() // Limpia la lista actual.
        episodes.addAll(
            episodesPerSeason[selectedSeason]?.mapIndexed { index, title ->
                // Recupera la calificación guardada para cada episodio.
                val key = "season_${selectedSeason}_episode_$index"
                val savedRating = sharedPreferences.getInt(key, 0)
                Episode(title, savedRating) // Crea un nuevo objeto `Episode`.
            } ?: emptyList()
        )
        adapter.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado.
    }

    /**
     * Reinicia las calificaciones de todos los episodios.
     */
    private fun resetRatings() {
        // Limpia todos los datos guardados en SharedPreferences.
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Reinicia las puntuaciones de la lista actual.
        episodes.forEach { it.rating = 0 }
        adapter.notifyDataSetChanged()

        // Muestra un mensaje de confirmación.
        Toast.makeText(requireContext(), "Puntuaciones reiniciadas", Toast.LENGTH_SHORT).show()
    }
}