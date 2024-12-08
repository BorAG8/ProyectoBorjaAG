package com.example.proyectoborjaag.casas

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectoborjaag.R

/**
 * Adaptador para mostrar la lista de personajes en un RecyclerView.
 */
class PersonajeAdapter : ListAdapter<Personaje, PersonajeAdapter.CharacterViewHolder>(
    DiffCallback()
) {

    /**
     * ViewHolder que contiene las vistas para mostrar la información de un personaje.
     */
    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.characterName)
        private val image: ImageView = view.findViewById(R.id.characterImage)

        /**
         * Enlaza los datos de un personaje con las vistas.
         *
         * @param character El personaje que se mostrará.
         */
        fun bind(character: Personaje) {
            name.text = character.fullName
            Glide.with(itemView.context)
                .load(character.image)
                .placeholder(R.drawable.placeholder) // Imagen mientras carga.
                .error(R.drawable.error_image) // Imagen si hay un error.
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Implementa una comparación eficiente de personajes para actualizaciones del RecyclerView.
     */
    class DiffCallback : DiffUtil.ItemCallback<Personaje>() {
        override fun areItemsTheSame(oldItem: Personaje, newItem: Personaje) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Personaje, newItem: Personaje) = oldItem == newItem
    }
}
