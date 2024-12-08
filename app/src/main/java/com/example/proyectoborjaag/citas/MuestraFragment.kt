package com.example.proyectoborjaag.citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyectoborjaag.R


class MuestraFragment : Fragment() {

    /**
     * Crea la vista del fragmento y muestra los detalles del personaje.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_muestra, container, false)

        // Recupera los argumentos pasados al fragmento.
        val name = arguments?.getString("name")
        val houseName = arguments?.getString("house")
        val quotes = arguments?.getStringArrayList("quotes")

        // Vincula los datos con las vistas.
        val characterNameTextView: TextView = view.findViewById(R.id.character_name)
        val houseNameTextView: TextView = view.findViewById(R.id.house_name)
        val quotesTextView: TextView = view.findViewById(R.id.quotes)

        characterNameTextView.text = name
        houseNameTextView.text = houseName
        quotesTextView.text = quotes?.joinToString("\n") ?: "No tiene citas"

        return view
    }
}
