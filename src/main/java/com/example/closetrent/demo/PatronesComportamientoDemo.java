package com.example.closetrent.demo;

import com.example.closetrent.model.*;
import com.example.closetrent.model.command.AlquilarPrendaCommand;
import com.example.closetrent.model.command.Command;
import com.example.closetrent.model.command.DevolverPrendaCommand;
import com.example.closetrent.model.command.Invoker;
import com.example.closetrent.model.iterator.Iterator;
import com.example.closetrent.model.iterator.PrendaCollection;
import com.example.closetrent.model.strategy.CostoNormal;
import com.example.closetrent.model.strategy.CostoPremium;
import com.example.closetrent.service.NegocioAlquiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de demostración del uso de los patrones de comportamiento implementados:
 * - Strategy: Para cálculo de costos
 * - Observer: Para notificaciones de cambio de estado
 * - Command: Para encapsular operaciones de alquiler/devolución
 * - Iterator: Para recorrer colecciones de prendas
 */
public class PatronesComportamientoDemo {

    private static final Logger logger = LoggerFactory.getLogger(PatronesComportamientoDemo.class);

    /**
     * Demostración del patrón Strategy - Diferentes estrategias de cálculo de costo.
     */
    public static void demostrarPatronStrategy() {
        logger.info("\n========== DEMOSTRACIÓN PATRÓN STRATEGY ==========");

        // Crear prendas de ejemplo
        List<Prenda> prendas = new ArrayList<>();
        prendas.add(new VestidoDama("VD001", "Rojo", "Elegance", "M", 150000.0, true, true, 2));
        prendas.add(new TrajeCaballero("TC001", "Negro", "Premium", "L", 200000.0,
                TrajeCaballero.TipoTraje.FRAC, TrajeCaballero.AccesorioCuello.CORBATIN));

        // Crear empleado y cliente ficticios
        Empleado empleado = new Empleado("E001", "María García", "Calle 123", "3001234567",
                "maria@example.com", "Gerente");
        Cliente cliente = new Cliente("C001", "Juan Pérez", "Av. 456", "3009876543",
                "juan@example.com");

        // Crear servicio de alquiler
        ServicioAlquiler servicio = new ServicioAlquiler(
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                empleado,
                cliente,
                prendas
        );

        // Calcular con estrategia normal
        servicio.setCostoStrategy(new CostoNormal());
        Double costoNormal = servicio.calcularValorTotal();
        logger.info("Costo con estrategia NORMAL: ${}", costoNormal);

        // Calcular con estrategia premium
        servicio.setCostoStrategy(new CostoPremium());
        Double costoPremium = servicio.calcularValorTotal();
        logger.info("Costo con estrategia PREMIUM (+20%): ${}", costoPremium);
    }

    /**
     * Demostración del patrón Observer - Notificaciones de cambio de estado.
     */
    public static void demostrarPatronObserver() {
        logger.info("\n========== DEMOSTRACIÓN PATRÓN OBSERVER ==========");

        // Crear prenda
        Prenda prenda = new VestidoDama("VD002", "Azul", "Fashion", "S", 180000.0,
                true, false, 1);

        // Crear clientes que se registran como observadores
        Cliente cliente1 = new Cliente("C002", "Ana López", "Calle 789", "3112223344",
                "ana@example.com");
        Cliente cliente2 = new Cliente("C003", "Carlos Ruiz", "Av. 101", "3223334455",
                "carlos@example.com");

        // Registrar observadores
        prenda.registrarObservador(cliente1);
        prenda.registrarObservador(cliente2);

        logger.info("Prenda inicialmente DISPONIBLE");

        // Cambiar estado - esto notificará a los observadores
        logger.info("\nCambiando estado de la prenda a NO DISPONIBLE...");
        prenda.cambiarEstado(false);

        // Mostrar notificaciones recibidas
        logger.info("\nNotificaciones recibidas por Cliente 1:");
        cliente1.obtenerNotificaciones().forEach(logger::info);

        logger.info("\nNotificaciones recibidas por Cliente 2:");
        cliente2.obtenerNotificaciones().forEach(logger::info);

        // Devolver la prenda
        logger.info("\nDevolviendo la prenda (cambiando a DISPONIBLE)...");
        prenda.cambiarEstado(true);

        logger.info("\nNuevas notificaciones de Cliente 1:");
        cliente1.obtenerNotificaciones().forEach(logger::info);
    }

    /**
     * Demostración del patrón Command - Encapsular operaciones.
     */
    public static void demostrarPatronCommand(NegocioAlquiler negocioAlquiler) {
        logger.info("\n========== DEMOSTRACIÓN PATRÓN COMMAND ==========");

        // Crear y registrar una prenda en el sistema
        Prenda prenda = new Disfraz("DF001", "Multicolor", "Fantasy", "M", 120000.0,
                "Superhéroe");
        negocioAlquiler.registrarPrenda(prenda);

        // Crear invoker
        Invoker invoker = new Invoker();

        try {
            // Crear y ejecutar comando de alquilar
            Command alquilarCmd = new AlquilarPrendaCommand(negocioAlquiler, "DF001");
            logger.info("\nEjecutando comando: {}", alquilarCmd.getDescripcion());
            invoker.ejecutarComando(alquilarCmd);
            logger.info("Estado de la prenda: {}",
                    prenda.getDisponible() ? "DISPONIBLE" : "NO DISPONIBLE");

            // Crear y ejecutar comando de devolver
            Command devolverCmd = new DevolverPrendaCommand(negocioAlquiler, "DF001");
            logger.info("\nEjecutando comando: {}", devolverCmd.getDescripcion());
            invoker.ejecutarComando(devolverCmd);
            logger.info("Estado de la prenda: {}",
                    prenda.getDisponible() ? "DISPONIBLE" : "NO DISPONIBLE");

            // Mostrar historial de comandos
            logger.info("\nHistorial de comandos ejecutados:");
            invoker.obtenerHistorial().forEach(cmd ->
                    logger.info("- {}", cmd.getDescripcion()));

        } catch (Exception e) {
            logger.error("Error ejecutando comandos", e);
        }
    }

    /**
     * Demostración del patrón Iterator - Recorrer colecciones.
     */
    public static void demostrarPatronIterator() {
        logger.info("\n========== DEMOSTRACIÓN PATRÓN ITERATOR ==========");

        // Crear colección de prendas
        PrendaCollection coleccion = new PrendaCollection();
        coleccion.agregar(new VestidoDama("VD003", "Verde", "Style", "L", 160000.0,
                false, true, 1));
        coleccion.agregar(new TrajeCaballero("TC002", "Gris", "Classic", "M", 180000.0,
                TrajeCaballero.TipoTraje.CONVENCIONAL, TrajeCaballero.AccesorioCuello.CORBATA));
        coleccion.agregar(new Disfraz("DF002", "Amarillo", "Fun", "S", 100000.0, "Pirata"));

        logger.info("Total de prendas en la colección: {}", coleccion.tamanio());

        // Crear iterador y recorrer la colección
        Iterator<Prenda> iterador = coleccion.crearIterador();

        logger.info("\nRecorriendo prendas con Iterator:");
        while (iterador.hasNext()) {
            Prenda prenda = iterador.next();
            logger.info("- {}", prenda.getInfo());
        }

        // Filtrar por disponibilidad
        logger.info("\nFiltrando prendas DISPONIBLES:");
        PrendaCollection disponibles = coleccion.filtrarPorDisponibilidad(true);
        Iterator<Prenda> iteradorDisponibles = disponibles.crearIterador();

        while (iteradorDisponibles.hasNext()) {
            Prenda prenda = iteradorDisponibles.next();
            logger.info("- {} (Ref: {})", prenda.getTipoPrenda(), prenda.getReferencia());
        }
    }

    /**
     * Método principal que ejecuta todas las demostraciones.
     * NOTA: Este método es solo para demostración. En producción, estos patrones
     * se usarían integrados en los servicios y controladores de la aplicación.
     */
    public static void main(String[] args) {
        logger.info("===============================================");
        logger.info("   DEMOSTRACIÓN DE PATRONES DE COMPORTAMIENTO");
        logger.info("   Sistema ClosetRent - Alquiler de Prendas");
        logger.info("===============================================");

        // 1. Patrón Strategy
        demostrarPatronStrategy();

        // 2. Patrón Observer
        demostrarPatronObserver();

        // 3. Patrón Iterator
        demostrarPatronIterator();

        logger.info("\n===============================================");
        logger.info("   DEMOSTRACIÓN COMPLETADA");
        logger.info("===============================================");

        // NOTA: El patrón Command requiere una instancia de NegocioAlquiler,
        // que en este demo standalone no está disponible. En la aplicación real,
        // se usa a través de los controladores con Spring dependency injection.
    }
}
