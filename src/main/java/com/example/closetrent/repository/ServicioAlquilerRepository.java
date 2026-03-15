package com.example.closetrent.repository;

import com.example.closetrent.model.Cliente;
import com.example.closetrent.model.ServicioAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para gestionar servicios de alquiler.
 */
@Repository
public interface ServicioAlquilerRepository extends JpaRepository<ServicioAlquiler, Long> {

    /**
     * Busca servicios por fecha de alquiler.
     */
    List<ServicioAlquiler> findByFechaAlquiler(LocalDate fechaAlquiler);

    /**
     * Busca servicios por cliente.
     */
    List<ServicioAlquiler> findByCliente(Cliente cliente);

    /**
     * Busca servicios vigentes (fecha igual o posterior a la fecha actual) por cliente.
     */
    @Query("SELECT s FROM ServicioAlquiler s WHERE s.cliente = :cliente " +
           "AND s.fechaAlquiler >= :fechaActual ORDER BY s.fechaAlquiler")
    List<ServicioAlquiler> findServiciosVigentesByCliente(
        @Param("cliente") Cliente cliente,
        @Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca servicios por rango de fechas.
     */
    @Query("SELECT s FROM ServicioAlquiler s WHERE s.fechaAlquiler BETWEEN :fechaInicio AND :fechaFin")
    List<ServicioAlquiler> findByFechaAlquilerBetween(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);
}
