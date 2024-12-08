package com.example.proyectoborjaag.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoborjaag.R


/**
 * Fragmento principal de la aplicación que contiene la navegación y los controles de música.
 */
class PrincipalFragment : Fragment() {

    // Obtiene una instancia compartida de `MusicViewModel` para gestionar el estado de la música.
    private val musicViewModel: MusicViewModel by activityViewModels()

    /**
     * Configura las vistas y la lógica del fragmento después de que se crea la vista.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController() // Controlador para manejar la navegación.

        // Configura los botones de navegación para ir a diferentes fragmentos.
        view.findViewById<Button>(R.id.btn_citas).setOnClickListener {
            navController.navigate(R.id.citasFragment) // Navega al fragmento de citas.
        }
        view.findViewById<Button>(R.id.btn_casas).setOnClickListener {
            navController.navigate(R.id.personajesFragment) // Navega al fragmento de casas.
        }
        view.findViewById<Button>(R.id.btn_episodios).setOnClickListener {
            navController.navigate(R.id.episodiosFragment) // Navega al fragmento de episodios.
        }
        view.findViewById<Button>(R.id.btn_ubicaciones).setOnClickListener {
            navController.navigate(R.id.ubicacionesFragment) // Navega al fragmento de ubicaciones.
        }

        // Configura los botones de control de música.
        val buttonPlay = view.findViewById<ImageButton>(R.id.buttonPlay)
        val buttonStop = view.findViewById<ImageButton>(R.id.buttonStop)

        // Observa el estado de reproducción de la música para habilitar/deshabilitar los botones.
        musicViewModel.isPlaying.observe(viewLifecycleOwner) { isPlaying ->
            buttonPlay.isEnabled = !isPlaying // Habilita el botón de "play" si no está reproduciendo.
            buttonStop.isEnabled = isPlaying // Habilita el botón de "stop" si está reproduciendo.
        }

        // Configura el botón de "play" para iniciar la música.
        buttonPlay.setOnClickListener {
            musicViewModel.startMusic(requireContext()) // Llama a la función para iniciar la música.
        }

        // Configura el botón de "stop" para detener la música.
        buttonStop.setOnClickListener {
            musicViewModel.stopMusic(requireContext()) // Llama a la función para detener la música.
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
    ): View {
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }
}

