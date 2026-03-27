package com.example.closetrent.model.command;

/**
 * Patrón Command: Interfaz para encapsular acciones como objetos.
 * Permite desacoplar la solicitud de la ejecución.
 */
public interface Command {

    /**
     * Ejecuta la acción encapsulada por el comando.
     *
     * @throws Exception Si ocurre un error durante la ejecución
     */
    void ejecutar() throws Exception;

    /**
     * Obtiene la descripción del comando.
     *
     * @return Descripción del comando
     */
    String getDescripcion();
}
