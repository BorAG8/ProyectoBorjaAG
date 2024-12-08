package com.example.proyectoborjaag.casas

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adaptador para gestionar las casas principales en el ViewPager2.
 *
 * @param fragment Fragmento asociado al adaptador.
 * @param houses Lista de nombres de las casas principales.
 */
class CasasAdapter(
    fragment: Fragment,
    private val houses: List<String>
) : FragmentStateAdapter(fragment) {

    /**
     * Devuelve el número total de casas (número de pestañas).
     */
    override fun getItemCount(): Int = houses.size

    /**
     * Crea una nueva instancia de `PersonajeFragment` para la posición actual.
     *
     * @param position Posición actual en el ViewPager2.
     * @return Una nueva instancia de `PersonajeFragment` con la casa correspondiente.
     */
    override fun createFragment(position: Int): Fragment {
        return PersonajeFragment.newInstance(houses[position])
    }
}

