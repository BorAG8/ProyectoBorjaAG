package com.example.proyectoborjaag.casas

/**
 * Representa un personaje con su información principal.
 *
 * @property id Identificador del personaje.
 * @property fullName Nombre completo del personaje.
 * @property image URL de la imagen del personaje.
 */
data class Personaje(
    val id: Int,
    val fullName: String,
    val image: String
)

