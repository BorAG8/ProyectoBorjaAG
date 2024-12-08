package com.example.proyectoborjaag.citas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoborjaag.R

/**
 * Adaptador para mostrar una lista de personajes en un RecyclerView.
 *
 * @param characters Lista de personajes a mostrar.
 * @param onCharacterClick Acción que se ejecutará al hacer clic en un personaje.
 */
class PersonajeDosAdapter(
    private val characters: List<PersonajeDos>,
    private val onCharacterClick: (PersonajeDos) -> Unit
) : RecyclerView.Adapter<PersonajeDosAdapter.CharacterViewHolder>() {

    /**
     * ViewHolder que contiene las vistas para un personaje.
     *
     * @param view La vista inflada para un elemento del RecyclerView.
     */
    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.character_name)
    }

    /**
     * Infla el layout para un elemento y crea un ViewHolder.
     *
     * @param parent El ViewGroup al que pertenece el ViewHolder.
     * @param viewType El tipo de vista del nuevo ViewHolder.
     * @return Una nueva instancia de `CharacterViewHolder`.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje2, parent, false)
        return CharacterViewHolder(view)
    }

    /**
     * Vincula un personaje con el ViewHolder en una posición específica.
     *
     * @param holder El ViewHolder que debe actualizarse.
     * @param position La posición del elemento en la lista.
     */
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.nameTextView.text = character.name
        holder.itemView.setOnClickListener { onCharacterClick(character) }
    }

    /**
     * Devuelve el número de elementos en la lista.
     *
     * @return El número total de personajes.
     */
    override fun getItemCount(): Int = characters.size
}



