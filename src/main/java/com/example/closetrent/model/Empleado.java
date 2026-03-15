package com.example.closetrent.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Empleado del negocio de alquiler.
 */
@Entity
@DiscriminatorValue("EMPLEADO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Empleado extends Persona {

    @Column(length = 50)
    private String cargo;

    /**
     * Constructor completo para Empleado.
     */
    public Empleado(String numeroIdentificacion, String nombre, String direccion,
                    String telefono, String correoElectronico, String cargo) {
        super(numeroIdentificacion, nombre, direccion, telefono, correoElectronico);
        this.cargo = cargo;
    }
}
