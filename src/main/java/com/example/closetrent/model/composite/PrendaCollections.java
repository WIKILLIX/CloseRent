package com.example.closetrent.model.composite;

import com.example.closetrent.model.Prenda;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Composite: Colección de prendas que puede contener otras colecciones.
 * Permite agrupar prendas de manera jerárquica.
 */
public class PrendaCollections implements IPrendaComponent {

    private String nombre;
    private List<IPrendaComponent> componentes;

    /**
     * Constructor con nombre de la colección.
     */
    public PrendaCollections(String nombre) {
        this.nombre = nombre;
        this.componentes = new ArrayList<>();
    }

    @Override
    public void agregar(IPrendaComponent component) {
        componentes.add(component);
    }

    @Override
    public void eliminar(IPrendaComponent component) {
        componentes.remove(component);
    }

    @Override
    public List<Prenda> obtenerPrendas() {
        List<Prenda> todasLasPrendas = new ArrayList<>();
        for (IPrendaComponent component : componentes) {
            todasLasPrendas.addAll(component.obtenerPrendas());
        }
        return todasLasPrendas;
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Colección: ").append(nombre).append("\n");
        info.append("Total de prendas: ").append(obtenerPrendas().size()).append("\n");
        for (IPrendaComponent component : componentes) {
            info.append("  - ").append(component.getInfo()).append("\n");
        }
        return info.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public int cantidadPrendas() {
        return obtenerPrendas().size();
    }
}
