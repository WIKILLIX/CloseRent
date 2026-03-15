package com.example.closetrent.model.composite;

import com.example.closetrent.model.Prenda;
import java.util.List;

/**
 * Patrón Composite: Interfaz para componentes de prendas.
 * Permite tratar objetos individuales y composiciones de manera uniforme.
 */
public interface IPrendaComponent {

    /**
     * Agrega un componente (prenda) a la colección.
     */
    void agregar(IPrendaComponent component);

    /**
     * Elimina un componente de la colección.
     */
    void eliminar(IPrendaComponent component);

    /**
     * Obtiene todas las prendas del componente.
     */
    List<Prenda> obtenerPrendas();

    /**
     * Obtiene información del componente.
     */
    String getInfo();
}
