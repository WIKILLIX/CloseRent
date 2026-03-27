package com.example.closetrent.model.strategy;

import com.example.closetrent.model.Prenda;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Estrategia de costo normal: Calcula el costo sumando el valor de alquiler de cada prenda.
 */
@Component
public class CostoNormal implements CostoStrategy {

    @Override
    public Double calcularValor(List<Prenda> prendas) {
        if (prendas == null || prendas.isEmpty()) {
            return 0.0;
        }

        return prendas.stream()
                .mapToDouble(Prenda::getValorAlquiler)
                .sum();
    }

    @Override
    public String getNombreEstrategia() {
        return "Costo Normal";
    }
}
