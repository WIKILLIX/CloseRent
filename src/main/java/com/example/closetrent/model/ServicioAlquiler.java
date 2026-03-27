package com.example.closetrent.model;

import com.example.closetrent.model.strategy.CostoNormal;
import com.example.closetrent.model.strategy.CostoStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un servicio de alquiler de prendas.
 * Implementa el patrón Strategy para el cálculo del costo.
 */
@Entity
@Table(name = "servicios_alquiler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioAlquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_servicio")
    private Long numeroServicio;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_alquiler", nullable = false)
    private LocalDate fechaAlquiler;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "servicio_prendas",
        joinColumns = @JoinColumn(name = "servicio_id"),
        inverseJoinColumns = @JoinColumn(name = "prenda_ref")
    )
    private List<Prenda> prendas = new ArrayList<>();

    /**
     * Patrón Strategy: Estrategia de cálculo de costo.
     * No se persiste en BD (transient).
     */
    @Transient
    private CostoStrategy costoStrategy = new CostoNormal();

    /**
     * Constructor sin el número de servicio (generado automáticamente).
     */
    public ServicioAlquiler(LocalDate fechaSolicitud, LocalDate fechaAlquiler,
                           Empleado empleado, Cliente cliente, List<Prenda> prendas) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAlquiler = fechaAlquiler;
        this.empleado = empleado;
        this.cliente = cliente;
        this.prendas = prendas != null ? prendas : new ArrayList<>();
        this.costoStrategy = new CostoNormal(); // Estrategia por defecto
    }

    /**
     * Constructor con estrategia de costo personalizada.
     */
    public ServicioAlquiler(LocalDate fechaSolicitud, LocalDate fechaAlquiler,
                           Empleado empleado, Cliente cliente, List<Prenda> prendas,
                           CostoStrategy costoStrategy) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAlquiler = fechaAlquiler;
        this.empleado = empleado;
        this.cliente = cliente;
        this.prendas = prendas != null ? prendas : new ArrayList<>();
        this.costoStrategy = costoStrategy != null ? costoStrategy : new CostoNormal();
    }

    /**
     * Agrega una prenda al servicio de alquiler.
     */
    public void agregarPrenda(Prenda prenda) {
        if (this.prendas == null) {
            this.prendas = new ArrayList<>();
        }
        this.prendas.add(prenda);
    }

    /**
     * Calcula el valor total del alquiler usando el patrón Strategy.
     * Delega el cálculo a la estrategia configurada.
     */
    public Double calcularValorTotal() {
        if (costoStrategy == null) {
            costoStrategy = new CostoNormal();
        }
        return costoStrategy.calcularValor(prendas);
    }

    /**
     * Establece la estrategia de cálculo de costo.
     *
     * @param costoStrategy La estrategia a utilizar
     */
    public void setCostoStrategy(CostoStrategy costoStrategy) {
        this.costoStrategy = costoStrategy != null ? costoStrategy : new CostoNormal();
    }
}
