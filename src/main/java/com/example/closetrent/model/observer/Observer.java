package com.example.closetrent.model.observer;

/**
 * Patrón Observer: Interfaz para observadores que reciben notificaciones.
 * Los observadores se suscriben a cambios de estado en las prendas.
 */
public interface Observer {

    /**
     * Método llamado cuando cambia el estado de una prenda observada.
     *
     * @param mensaje Mensaje descriptivo del cambio
     * @param prendaReferencia Referencia de la prenda que cambió
     * @param nuevoEstado Nuevo estado de disponibilidad
     */
    void actualizarEstado(String mensaje, String prendaReferencia, Boolean nuevoEstado);
}
