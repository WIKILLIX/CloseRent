package com.example.closetrent.model.iterator;

import com.example.closetrent.model.Prenda;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Iterator: Colección de prendas que proporciona un iterador.
 * Encapsula la estructura interna de la colección.
 */
public class PrendaCollection {

    private final List<Prenda> prendas;

    /**
     * Constructor vacío.
     */
    public PrendaCollection() {
        this.prendas = new ArrayList<>();
    }

    /**
     * Constructor con lista inicial de prendas.
     *
     * @param prendas Lista inicial de prendas
     */
    public PrendaCollection(List<Prenda> prendas) {
        this.prendas = new ArrayList<>(prendas);
    }

    /**
     * Agrega una prenda a la colección.
     *
     * @param prenda Prenda a agregar
     */
    public void agregar(Prenda prenda) {
        if (prenda != null) {
            prendas.add(prenda);
        }
    }

    /**
     * Elimina una prenda de la colección.
     *
     * @param prenda Prenda a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminar(Prenda prenda) {
        return prendas.remove(prenda);
    }

    /**
     * Crea y retorna un iterador para recorrer la colección.
     *
     * @return Iterador de prendas
     */
    public Iterator<Prenda> crearIterador() {
        return new PrendaIterator(prendas);
    }

    /**
     * Obtiene el tamaño de la colección.
     *
     * @return Número de prendas en la colección
     */
    public int tamanio() {
        return prendas.size();
    }

    /**
     * Verifica si la colección está vacía.
     *
     * @return true si está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return prendas.isEmpty();
    }

    /**
     * Limpia todas las prendas de la colección.
     */
    public void limpiar() {
        prendas.clear();
    }

    /**
     * Filtra prendas por disponibilidad.
     *
     * @param disponible Estado de disponibilidad a filtrar
     * @return Nueva colección con prendas filtradas
     */
    public PrendaCollection filtrarPorDisponibilidad(boolean disponible) {
        List<Prenda> prendasFiltradas = prendas.stream()
                .filter(p -> p.getDisponible() == disponible)
                .toList();
        return new PrendaCollection(prendasFiltradas);
    }

    /**
     * Filtra prendas por talla.
     *
     * @param talla Talla a filtrar
     * @return Nueva colección con prendas filtradas
     */
    public PrendaCollection filtrarPorTalla(String talla) {
        List<Prenda> prendasFiltradas = prendas.stream()
                .filter(p -> p.getTalla().equalsIgnoreCase(talla))
                .toList();
        return new PrendaCollection(prendasFiltradas);
    }
}
