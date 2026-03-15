package com.example.closetrent.model.composite;

import com.example.closetrent.model.Prenda;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Composite: Componente hoja que representa una prenda individual.
 */
public class PrendaIndividual implements IPrendaComponent {

    private Prenda prenda;

    /**
     * Constructor con la prenda.
     */
    public PrendaIndividual(Prenda prenda) {
        this.prenda = prenda;
    }

    @Override
    public void agregar(IPrendaComponent component) {
        throw new UnsupportedOperationException(
            "No se pueden agregar componentes a una prenda individual");
    }

    @Override
    public void eliminar(IPrendaComponent component) {
        throw new UnsupportedOperationException(
            "No se pueden eliminar componentes de una prenda individual");
    }

    @Override
    public List<Prenda> obtenerPrendas() {
        List<Prenda> lista = new ArrayList<>();
        lista.add(prenda);
        return lista;
    }

    @Override
    public String getInfo() {
        return prenda.getInfo();
    }

    public Prenda getPrenda() {
        return prenda;
    }
}
