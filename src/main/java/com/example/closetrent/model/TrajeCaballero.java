package com.example.closetrent.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Traje de Caballero para alquiler.
 */
@Entity
@DiscriminatorValue("TRAJE_CABALLERO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TrajeCaballero extends Prenda {

    /**
     * Enumeración para los tipos de traje de caballero.
     */
    public enum TipoTraje {
        CONVENCIONAL, FRAC, SACOLEVA, OTRO
    }

    /**
     * Enumeración para los accesorios de cuello del traje.
     */
    public enum AccesorioCuello {
        CORBATA, CORBATIN, PLASTRON, NINGUNO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_traje")
    private TipoTraje tipoTraje;

    @Enumerated(EnumType.STRING)
    @Column(name = "accesorio_cuello")
    private AccesorioCuello accesorioCuello;

    /**
     * Constructor completo para TrajeCaballero.
     */
    public TrajeCaballero(String referencia, String color, String marca, String talla,
                          Double valorAlquiler, TipoTraje tipoTraje,
                          AccesorioCuello accesorioCuello) {
        super(referencia, color, marca, talla, valorAlquiler, true);
        this.tipoTraje = tipoTraje;
        this.accesorioCuello = accesorioCuello;
    }

    @Override
    public String getInfo() {
        return String.format("Traje Caballero - Ref: %s, Color: %s, Marca: %s, Talla: %s, " +
                           "Tipo: %s, Accesorio: %s, Valor: $%.2f",
                getReferencia(), getColor(), getMarca(), getTalla(),
                tipoTraje, accesorioCuello, getValorAlquiler());
    }

    @Override
    public String getTipoPrenda() {
        return "Traje de Caballero";
    }
}
