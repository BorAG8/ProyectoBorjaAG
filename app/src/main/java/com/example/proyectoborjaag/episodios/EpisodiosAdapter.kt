package com.example.proyectoborjaag.episodios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoborjaag.R

/**
 * Adaptador para mostrar la lista de episodios en un RecyclerView.
 *
 * @param items Lista de episodios a mostrar.
 * @param onClick Callback que se ejecuta cuando se hace clic en un episodio.
 */
class EpisodiosAdapter(
    private val items: List<Episode>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<EpisodiosAdapter.EpisodeViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para un elemento del RecyclerView.
     *
     * @param parent El ViewGroup al que pertenece el ViewHolder.
     * @param viewType El tipo de vista (si hubiera múltiples layouts).
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    /**
     * Vincula los datos de un episodio con el ViewHolder.
     *
     * @param holder El ViewHolder que debe actualizarse.
     * @param position La posición del episodio en la lista.
     */
    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item) // Actualiza las vistas con los datos del episodio.
        holder.itemView.setOnClickListener {
            onClick(position) // Ejecuta el callback al hacer clic.
        }
    }

    /**
     * Devuelve el número total de episodios.
     */
    override fun getItemCount(): Int = items.size

    /**
     * ViewHolder que gestiona las vistas de un episodio.
     *
     * @param itemView La vista inflada del layout de un episodio.
     */
    class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val episodeName: TextView = itemView.findViewById(R.id.tv_episode_name)
        private val episodeRating: TextView = itemView.findViewById(R.id.tv_episode_rating)

        /**
         * Vincula los datos de un episodio con las vistas.
         *
         * @param episode El episodio cuyos datos se mostrarán.
         */
        fun bind(episode: Episode) {
            episodeName.text = episode.name
            episodeRating.text = episode.rating.toString()
        }
    }
}

