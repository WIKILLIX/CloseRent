package com.example.closetrent.service;

import com.example.closetrent.model.Prenda;
import com.example.closetrent.model.decorator.IPrendaDecorator;
import com.example.closetrent.model.decorator.PrendaBaseDecorator;
import com.example.closetrent.model.decorator.PrendaPrioridad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Servicio para gestionar la cola de lavandería.
 * Maneja prendas con y sin prioridad usando el patrón Decorator.
 */
@Service
public class LavanderiaService {

    private final Queue<IPrendaDecorator> colaPrioridad;
    private final Queue<IPrendaDecorator> colaNormal;

    public LavanderiaService() {
        this.colaPrioridad = new LinkedList<>();
        this.colaNormal = new LinkedList<>();
    }

    /**
     * Registra una prenda para envío a lavandería sin prioridad.
     */
    public void registrarPrendaNormal(Prenda prenda) {
        IPrendaDecorator prendaDecorada = new PrendaBaseDecorator(prenda);
        colaNormal.offer(prendaDecorada);
    }

    /**
     * Registra una prenda para envío a lavandería con prioridad.
     */
    public void registrarPrendaPrioridad(Prenda prenda, String motivo) {
        IPrendaDecorator prendaDecorada = new PrendaPrioridad(prenda, motivo);
        colaPrioridad.offer(prendaDecorada);
    }

    /**
     * Obtiene el listado completo de prendas en cola para lavandería.
     * Primero las de prioridad, luego las normales.
     */
    public List<IPrendaDecorator> obtenerListadoCompleto() {
        List<IPrendaDecorator> listado = new ArrayList<>();
        listado.addAll(colaPrioridad);
        listado.addAll(colaNormal);
        return listado;
    }

    /**
     * Envía una cantidad específica de prendas a lavandería.
     * Retorna las prendas enviadas.
     */
    public List<IPrendaDecorator> enviarPrendasALavanderia(int cantidad) {
        List<IPrendaDecorator> prendasEnviadas = new ArrayList<>();

        // Primero se envían las de prioridad
        while (!colaPrioridad.isEmpty() && prendasEnviadas.size() < cantidad) {
            prendasEnviadas.add(colaPrioridad.poll());
        }

        // Luego las normales si aún hay capacidad
        while (!colaNormal.isEmpty() && prendasEnviadas.size() < cantidad) {
            prendasEnviadas.add(colaNormal.poll());
        }

        return prendasEnviadas;
    }

    /**
     * Obtiene la cantidad total de prendas en cola.
     */
    public int cantidadTotal() {
        return colaPrioridad.size() + colaNormal.size();
    }

    /**
     * Obtiene la cantidad de prendas con prioridad.
     */
    public int cantidadPrioridad() {
        return colaPrioridad.size();
    }

    /**
     * Obtiene la cantidad de prendas normales.
     */
    public int cantidadNormal() {
        return colaNormal.size();
    }

    /**
     * Limpia todas las colas.
     */
    public void limpiarColas() {
        colaPrioridad.clear();
        colaNormal.clear();
    }
}
