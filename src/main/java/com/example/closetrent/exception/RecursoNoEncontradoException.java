package com.example.closetrent.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado en el sistema.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    private final String tipoRecurso;
    private final String identificador;

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje descriptivo del error
     */
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
        this.tipoRecurso = null;
        this.identificador = null;
    }

    /**
     * Constructor con tipo de recurso e identificador.
     *
     * @param tipoRecurso Tipo de recurso (ej: "Cliente", "Empleado", "Prenda")
     * @param identificador Identificador del recurso no encontrado
     */
    public RecursoNoEncontradoException(String tipoRecurso, String identificador) {
        super(String.format("No se encontró el %s con identificador '%s'",
                tipoRecurso, identificador));
        this.tipoRecurso = tipoRecurso;
        this.identificador = identificador;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public String getIdentificador() {
        return identificador;
    }
}
