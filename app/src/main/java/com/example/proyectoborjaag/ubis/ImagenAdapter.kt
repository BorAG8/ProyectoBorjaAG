package com.example.proyectoborjaag.ubis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoborjaag.R

/**
 * Adaptador para un RecyclerView que muestra imágenes con nombres y casillas de verificación.
 *
 * @param items Lista de elementos de tipo `ItemImagen` que se mostrarán.
 * @param onCheckedChange Callback que se ejecuta cuando cambia el estado de la casilla de verificación.
 */
class ImagenAdapter(
    private val items: List<ItemImagen>,
    private val onCheckedChange: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder>() {

    /**
     * ViewHolder que contiene las vistas para un elemento de tipo `ItemImagen`.
     *
     * @param view Vista inflada para un elemento de la lista.
     */
    inner class ImagenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.item_image) // Imagen del elemento.
        private val nameTextView: TextView = view.findViewById(R.id.item_name) // Nombre del elemento.
        private val checkBox: CheckBox = view.findViewById(R.id.item_checkbox) // Casilla de verificación.

        /**
         * Vincula los datos de un `ItemImagen` con las vistas del ViewHolder.
         *
         * @param item Elemento `ItemImagen` cuyos datos se van a mostrar.
         */
        fun bind(item: ItemImagen) {
            imageView.setImageResource(item.id) // Asigna la imagen.
            nameTextView.text = item.name // Asigna el nombre.
            checkBox.isChecked = item.isChecked // Configura el estado de la casilla de verificación.

            // Escucha los cambios en la casilla de verificación y ejecuta el callback.
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange(adapterPosition, isChecked)
            }
        }
    }

    /**
     * Crea un nuevo ViewHolder para un elemento de la lista.
     *
     * @param parent El ViewGroup al que pertenece el ViewHolder.
     * @param viewType Tipo de vista (por si se usan diferentes layouts).
     * @return Un nuevo `ImagenViewHolder` con la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imagen, parent, false)
        return ImagenViewHolder(view)
    }

    /**
     * Vincula los datos de un elemento con el ViewHolder.
     *
     * @param holder El ViewHolder que debe actualizarse.
     * @param position La posición del elemento en la lista.
     */
    override fun onBindViewHolder(holder: ImagenViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Devuelve el número total de elementos en la lista.
     */
    override fun getItemCount(): Int = items.size
}
