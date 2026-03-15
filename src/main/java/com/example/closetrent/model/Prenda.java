package com.example.closetrent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase abstracta base para representar una prenda de vestir.
 * Utiliza herencia de tabla única (SINGLE_TABLE) para VestidoDama, TrajeCaballero y Disfraz.
 */
@Entity
@Table(name = "prendas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_prenda", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Prenda {

    @Id
    @Column(length = 20)
    private String referencia;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 10)
    private String talla;

    @Column(name = "valor_alquiler", nullable = false)
    private Double valorAlquiler;

    @Column(name = "disponible")
    private Boolean disponible = true;

    /**
     * Método abstracto para obtener información específica de la prenda.
     * @return String con información detallada de la prenda
     */
    public abstract String getInfo();

    /**
     * Método abstracto para obtener el tipo de prenda.
     * @return String con el tipo de prenda
     */
    public abstract String getTipoPrenda();
}
