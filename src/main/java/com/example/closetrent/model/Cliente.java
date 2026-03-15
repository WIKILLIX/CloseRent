package com.example.closetrent.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Cliente del negocio de alquiler.
 */
@Entity
@DiscriminatorValue("CLIENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cliente extends Persona {

    /**
     * Constructor completo para Cliente.
     */
    public Cliente(String numeroIdentificacion, String nombre, String direccion,
                   String telefono, String correoElectronico) {
        super(numeroIdentificacion, nombre, direccion, telefono, correoElectronico);
    }
}
