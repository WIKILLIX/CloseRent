package com.example.closetrent.model;

import com.example.closetrent.model.observer.Observer;
import com.example.closetrent.model.observer.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta base para representar una prenda de vestir.
 * Utiliza herencia de tabla única (SINGLE_TABLE) para VestidoDama, TrajeCaballero y Disfraz.
 * Implementa el patrón Observer como Subject para notificar cambios de estado.
 */
@Entity
@Table(name = "prendas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_prenda", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class Prenda implements Subject {

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
     * Patrón Observer: Lista de observadores interesados en cambios de estado.
     * No se persiste en BD (transient).
     */
    @Transient
    private List<Observer> observadores = new ArrayList<>();

    /**
     * Constructor para las subclases (sin el campo transient observadores).
     */
    public Prenda(String referencia, String color, String marca, String talla,
                  Double valorAlquiler, Boolean disponible) {
        this.referencia = referencia;
        this.color = color;
        this.marca = marca;
        this.talla = talla;
        this.valorAlquiler = valorAlquiler;
        this.disponible = disponible;
        this.observadores = new ArrayList<>();
    }

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

    // ========== Implementación del patrón Observer ==========

    @Override
    public void registrarObservador(Observer observer) {
        if (observadores == null) {
            observadores = new ArrayList<>();
        }
        if (!observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    @Override
    public void eliminarObservador(Observer observer) {
        if (observadores != null) {
            observadores.remove(observer);
        }
    }

    @Override
    public void notificar() {
        if (observadores == null) {
            observadores = new ArrayList<>();
        }
        String mensaje = "La prenda " + referencia + " ha cambiado su estado de disponibilidad a: " +
                        (disponible ? "DISPONIBLE" : "NO DISPONIBLE");

        for (Observer observer : observadores) {
            observer.actualizarEstado(mensaje, referencia, disponible);
        }
    }

    /**
     * Cambia el estado de disponibilidad y notifica a los observadores.
     *
     * @param nuevoEstado Nuevo estado de disponibilidad
     */
    public void cambiarEstado(Boolean nuevoEstado) {
        this.disponible = nuevoEstado;
        notificar();
    }
}
