package com.example.closetrent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un servicio de alquiler de prendas.
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
     * Constructor sin el número de servicio (generado automáticamente).
     */
    public ServicioAlquiler(LocalDate fechaSolicitud, LocalDate fechaAlquiler,
                           Empleado empleado, Cliente cliente, List<Prenda> prendas) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAlquiler = fechaAlquiler;
        this.empleado = empleado;
        this.cliente = cliente;
        this.prendas = prendas != null ? prendas : new ArrayList<>();
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
     * Calcula el valor total del alquiler.
     */
    public Double calcularValorTotal() {
        return prendas.stream()
                .mapToDouble(Prenda::getValorAlquiler)
                .sum();
    }
}
