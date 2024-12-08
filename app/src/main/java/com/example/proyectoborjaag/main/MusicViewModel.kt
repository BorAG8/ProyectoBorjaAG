package com.example.proyectoborjaag.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel que gestiona el estado de reproducción de música.
 */
class MusicViewModel : ViewModel() {

    private val _isPlaying = MutableLiveData<Boolean>() // Estado interno de reproducción.
    val isPlaying: LiveData<Boolean> get() = _isPlaying // Estado observable de reproducción.

    /**
     * Inicia la reproducción de música utilizando el servicio `MusicService`.
     *
     * @param context Contexto para iniciar el servicio.
     */
    fun startMusic(context: Context) {
        _isPlaying.value = true // Actualiza el estado a "reproduciendo".
        context.startService(Intent(context, MusicService::class.java)) // Inicia el servicio.
    }

    /**
     * Detiene la reproducción de música utilizando el servicio `MusicService`.
     *
     * @param context Contexto para detener el servicio.
     */
    fun stopMusic(context: Context) {
        _isPlaying.value = false // Actualiza el estado a "detenido".
        context.stopService(Intent(context, MusicService::class.java)) // Detiene el servicio.
    }
}


