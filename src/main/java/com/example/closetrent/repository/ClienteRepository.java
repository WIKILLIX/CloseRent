package com.example.closetrent.repository;

import com.example.closetrent.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para gestionar clientes.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    /**
     * Busca un cliente por nombre (búsqueda parcial).
     */
    Cliente findByNombreContainingIgnoreCase(String nombre);
}
