package com.example.closetrent.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura excepciones y proporciona respuestas amigables al usuario.
 *
 * NOTA: Este manejador proporciona respuestas REST (JSON).
 * Para aplicaciones web con vistas, los controladores deben manejar
 * las excepciones directamente usando RedirectAttributes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja excepciones cuando un recurso ya existe (duplicado).
     */
    @ExceptionHandler(RecursoYaExisteException.class)
    public ModelAndView handleRecursoYaExiste(RecursoYaExisteException ex,
                                              HttpServletRequest request) {
        logger.warn("Recurso duplicado: {}", ex.getMessage());

        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Recurso Duplicado");
        mav.addObject("mensaje", ex.getMessage());
        mav.addObject("tipoMensaje", "warning");
        mav.setViewName("error/duplicado");

        return mav;
    }

    /**
     * Maneja excepciones cuando no se encuentra un recurso.
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ModelAndView handleRecursoNoEncontrado(RecursoNoEncontradoException ex,
                                                  HttpServletRequest request) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());

        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Recurso No Encontrado");
        mav.addObject("mensaje", ex.getMessage());
        mav.addObject("tipoMensaje", "info");
        mav.setViewName("error/no-encontrado");

        return mav;
    }

    /**
     * Maneja excepciones de operaciones no permitidas.
     */
    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ModelAndView handleOperacionNoPermitida(OperacionNoPermitidaException ex,
                                                   HttpServletRequest request) {
        logger.warn("Operación no permitida: {}", ex.getMessage());

        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Operación No Permitida");
        mav.addObject("mensaje", ex.getMessage());
        mav.addObject("tipoMensaje", "warning");
        mav.setViewName("error/operacion-no-permitida");

        return mav;
    }

    /**
     * Maneja excepciones de integridad de datos de la BD.
     * Esta es una red de seguridad para errores que no fueron convertidos
     * a excepciones personalizadas.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                     HttpServletRequest request) {
        logger.error("Error de integridad de datos: {}", ex.getMessage());

        String mensajeAmigable;
        if (ex.getMessage().contains("Duplicate entry")) {
            mensajeAmigable = "El registro que intenta crear ya existe en el sistema. " +
                            "Por favor, verifique los datos e intente nuevamente.";
        } else if (ex.getMessage().contains("foreign key constraint")) {
            mensajeAmigable = "No se puede realizar la operación porque existen datos relacionados. " +
                            "Por favor, verifique las dependencias.";
        } else {
            mensajeAmigable = "Ocurrió un error con los datos. Por favor, verifique la información e intente nuevamente.";
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Error de Datos");
        mav.addObject("mensaje", mensajeAmigable);
        mav.addObject("tipoMensaje", "error");
        mav.setViewName("error/general");

        return mav;
    }

    /**
     * Maneja cualquier otra excepción no capturada específicamente.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("Error no manejado: ", ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Error del Sistema");
        mav.addObject("mensaje", "Ha ocurrido un error inesperado. Por favor, intente nuevamente más tarde.");
        mav.addObject("tipoMensaje", "error");
        mav.setViewName("error/general");

        return mav;
    }

    // ============ Métodos para API REST (JSON responses) ============

    /**
     * Clase para respuestas de error en formato JSON.
     */
    public static class ErrorResponse {
        private String error;
        private String mensaje;
        private String tipo;

        public ErrorResponse(String error, String mensaje, String tipo) {
            this.error = error;
            this.mensaje = mensaje;
            this.tipo = tipo;
        }

        public String getError() {
            return error;
        }

        public String getMensaje() {
            return mensaje;
        }

        public String getTipo() {
            return tipo;
        }
    }

    /**
     * Versión REST para RecursoYaExisteException.
     * Útil si tienes endpoints REST además de las vistas.
     */
    public ResponseEntity<ErrorResponse> handleRecursoYaExisteRest(RecursoYaExisteException ex) {
        logger.warn("Recurso duplicado (REST): {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            "RECURSO_DUPLICADO",
            ex.getMessage(),
            "warning"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Versión REST para RecursoNoEncontradoException.
     */
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontradoRest(RecursoNoEncontradoException ex) {
        logger.warn("Recurso no encontrado (REST): {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            "RECURSO_NO_ENCONTRADO",
            ex.getMessage(),
            "info"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Versión REST para OperacionNoPermitidaException.
     */
    public ResponseEntity<ErrorResponse> handleOperacionNoPermitidaRest(OperacionNoPermitidaException ex) {
        logger.warn("Operación no permitida (REST): {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            "OPERACION_NO_PERMITIDA",
            ex.getMessage(),
            "warning"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
