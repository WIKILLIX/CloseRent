package com.example.closetrent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase abstracta base para representar una persona en el sistema.
 * Utiliza herencia de tabla única (SINGLE_TABLE) para Cliente y Empleado.
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Persona {

    @Id
    @Column(name = "numero_identificacion", length = 20)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String correoElectronico;
}
