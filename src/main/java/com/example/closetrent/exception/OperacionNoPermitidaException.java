package com.example.closetrent.exception;

/**
 * Excepción lanzada cuando se intenta realizar una operación que no está permitida.
 * Por ejemplo, alquilar una prenda que ya está alquilada.
 */
public class OperacionNoPermitidaException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje descriptivo del error
     */
    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param mensaje Mensaje descriptivo del error
     * @param causa Excepción original que causó el error
     */
    public OperacionNoPermitidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
