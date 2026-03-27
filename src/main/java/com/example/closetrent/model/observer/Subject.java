package com.example.closetrent.model.observer;

/**
 * Patrón Observer: Interfaz para sujetos observables.
 * Los sujetos mantienen una lista de observadores y los notifican de cambios.
 */
public interface Subject {

    /**
     * Registra un observador para recibir notificaciones.
     *
     * @param observer El observador a registrar
     */
    void registrarObservador(Observer observer);

    /**
     * Elimina un observador de la lista de notificaciones.
     *
     * @param observer El observador a eliminar
     */
    void eliminarObservador(Observer observer);

    /**
     * Notifica a todos los observadores registrados sobre un cambio de estado.
     */
    void notificar();
}
