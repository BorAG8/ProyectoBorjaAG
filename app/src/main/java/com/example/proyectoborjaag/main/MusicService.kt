package com.example.proyectoborjaag.main

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.proyectoborjaag.R

/**
 * Servicio que gestiona la reproducción de música en segundo plano.
 */
class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null // MediaPlayer para reproducir música.

    /**
     * Se invoca cuando el servicio se crea por primera vez.
     * Inicializa el MediaPlayer y comienza la reproducción en bucle.
     */
    override fun onCreate() {
        super.onCreate()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bso).apply {
                isLooping = true // Reproduce la música en bucle.
                start() // Comienza la reproducción.
            }
        }
    }

    /**
     * Se invoca cuando el servicio se destruye.
     * Libera los recursos del MediaPlayer.
     */
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Libera los recursos asociados al MediaPlayer.
        mediaPlayer = null // Elimina la referencia al MediaPlayer.
    }

    /**
     * Se invoca cada vez que se llama a `startService` para este servicio.
     *
     * @param intent La intención que inició el servicio.
     * @param flags Bandera adicional sobre cómo iniciar el servicio.
     * @param startId ID único para esta instancia de inicio del servicio.
     * @return `START_STICKY` para que el servicio se reinicie automáticamente si el sistema lo elimina.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    /**
     * Método requerido para los servicios, pero no usado aquí ya que no se necesita comunicación.
     *
     * @param intent La intención que vinculó al servicio.
     * @return Siempre devuelve `null` ya que este servicio no permite vinculación.
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}



