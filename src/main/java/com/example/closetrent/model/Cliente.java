package com.example.closetrent.model;

import com.example.closetrent.model.observer.Observer;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Cliente del negocio de alquiler.
 * Implementa el patrón Observer para recibir notificaciones de cambios en prendas.
 */
@Entity
@DiscriminatorValue("CLIENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cliente extends Persona implements Observer {

    private static final Logger logger = LoggerFactory.getLogger(Cliente.class);

    /**
     * Patrón Observer: Lista de notificaciones recibidas.
     * No se persiste en BD (transient).
     */
    @Transient
    private List<String> notificaciones = new ArrayList<>();

    /**
     * Constructor completo para Cliente.
     */
    public Cliente(String numeroIdentificacion, String nombre, String direccion,
                   String telefono, String correoElectronico) {
        super(numeroIdentificacion, nombre, direccion, telefono, correoElectronico);
        this.notificaciones = new ArrayList<>();
    }

    // ========== Implementación del patrón Observer ==========

    @Override
    public void actualizarEstado(String mensaje, String prendaReferencia, Boolean nuevoEstado) {
        if (notificaciones == null) {
            notificaciones = new ArrayList<>();
        }

        String notificacion = String.format(
            "[NOTIFICACIÓN] Cliente %s (%s): %s",
            getNombre(),
            getNumeroIdentificacion(),
            mensaje
        );

        notificaciones.add(notificacion);

        // Log para seguimiento
        logger.info("Cliente {} notificado: Prenda {} ahora está {}",
                   getNombre(),
                   prendaReferencia,
                   nuevoEstado ? "DISPONIBLE" : "NO DISPONIBLE");
    }

    /**
     * Obtiene todas las notificaciones recibidas por el cliente.
     *
     * @return Lista de notificaciones
     */
    public List<String> obtenerNotificaciones() {
        if (notificaciones == null) {
            notificaciones = new ArrayList<>();
        }
        return new ArrayList<>(notificaciones);
    }

    /**
     * Limpia todas las notificaciones del cliente.
     */
    public void limpiarNotificaciones() {
        if (notificaciones != null) {
            notificaciones.clear();
        }
    }
}
