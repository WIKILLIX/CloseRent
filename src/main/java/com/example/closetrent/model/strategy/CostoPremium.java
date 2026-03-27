package com.example.closetrent.model.strategy;

import com.example.closetrent.model.Prenda;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Estrategia de costo premium: Calcula el costo aplicando un recargo del 20%.
 * Útil para alquileres de última hora, eventos especiales o servicios premium.
 */
@Component
public class CostoPremium implements CostoStrategy {

    private static final double RECARGO_PREMIUM = 1.20; // 20% adicional

    @Override
    public Double calcularValor(List<Prenda> prendas) {
        if (prendas == null || prendas.isEmpty()) {
            return 0.0;
        }

        double costoBase = prendas.stream()
                .mapToDouble(Prenda::getValorAlquiler)
                .sum();

        return costoBase * RECARGO_PREMIUM;
    }

    @Override
    public String getNombreEstrategia() {
        return "Costo Premium (+20%)";
    }
}
