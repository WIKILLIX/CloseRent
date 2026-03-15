package com.example.closetrent.repository;

import com.example.closetrent.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para gestionar empleados.
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    /**
     * Busca empleados por cargo.
     */
    List<Empleado> findByCargo(String cargo);
}
