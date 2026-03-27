package com.example.closetrent.exception;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe en el sistema.
 * Por ejemplo, cuando se intenta registrar un cliente con un número de identificación duplicado.
 */
public class RecursoYaExisteException extends RuntimeException {

    private final String tipoRecurso;
    private final String identificador;

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje descriptivo del error
     */
    public RecursoYaExisteException(String mensaje) {
        super(mensaje);
        this.tipoRecurso = null;
        this.identificador = null;
    }

    /**
     * Constructor con tipo de recurso e identificador.
     *
     * @param tipoRecurso Tipo de recurso (ej: "Cliente", "Empleado", "Prenda")
     * @param identificador Identificador del recurso que ya existe
     */
    public RecursoYaExisteException(String tipoRecurso, String identificador) {
        super(String.format("Ya existe un %s con el identificador '%s' en el sistema",
                tipoRecurso, identificador));
        this.tipoRecurso = tipoRecurso;
        this.identificador = identificador;
    }

    /**
     * Constructor con tipo de recurso, identificador y causa.
     *
     * @param tipoRecurso Tipo de recurso
     * @param identificador Identificador del recurso
     * @param causa Excepción original que causó el error
     */
    public RecursoYaExisteException(String tipoRecurso, String identificador, Throwable causa) {
        super(String.format("Ya existe un %s con el identificador '%s' en el sistema",
                tipoRecurso, identificador), causa);
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
