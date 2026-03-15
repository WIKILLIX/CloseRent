package com.example.closetrent.repository;

import com.example.closetrent.model.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para gestionar prendas.
 */
@Repository
public interface PrendaRepository extends JpaRepository<Prenda, String> {

    /**
     * Busca prendas por talla.
     */
    List<Prenda> findByTalla(String talla);

    /**
     * Busca prendas disponibles.
     */
    List<Prenda> findByDisponible(Boolean disponible);

    /**
     * Busca prendas por talla y disponibilidad.
     */
    List<Prenda> findByTallaAndDisponible(String talla, Boolean disponible);

    /**
     * Busca prendas por tipo (discriminator).
     */
    @Query("SELECT p FROM Prenda p WHERE TYPE(p) = :tipo")
    List<Prenda> findByTipo(Class<? extends Prenda> tipo);
}
