package com.example.closetrent.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Disfraz para alquiler.
 */
@Entity
@DiscriminatorValue("DISFRAZ")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Disfraz extends Prenda {

    @Column(name = "nombre_disfraz", length = 100)
    private String nombreDisfraz;

    /**
     * Constructor completo para Disfraz.
     */
    public Disfraz(String referencia, String color, String marca, String talla,
                   Double valorAlquiler, String nombreDisfraz) {
        super(referencia, color, marca, talla, valorAlquiler, true);
        this.nombreDisfraz = nombreDisfraz;
    }

    @Override
    public String getInfo() {
        return String.format("Disfraz - Ref: %s, Nombre: %s, Color: %s, Marca: %s, " +
                           "Talla: %s, Valor: $%.2f",
                getReferencia(), getNombreDisfraz(), getColor(), getMarca(),
                getTalla(), getValorAlquiler());
    }

    @Override
    public String getTipoPrenda() {
        return "Disfraz";
    }
}
