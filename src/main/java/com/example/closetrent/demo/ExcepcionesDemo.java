package com.example.closetrent.demo;

import com.example.closetrent.exception.RecursoYaExisteException;
import com.example.closetrent.exception.RecursoNoEncontradoException;
import com.example.closetrent.exception.OperacionNoPermitidaException;
import com.example.closetrent.model.Cliente;
import com.example.closetrent.service.NegocioAlquiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de demostración del manejo de excepciones personalizadas.
 * Muestra cómo los errores técnicos se convierten en mensajes amigables.
 */
public class ExcepcionesDemo {

    private static final Logger logger = LoggerFactory.getLogger(ExcepcionesDemo.class);

    /**
     * Demuestra el manejo de cliente duplicado.
     */
    public static void demostrarClienteDuplicado(NegocioAlquiler negocio) {
        logger.info("\n========== DEMO: Cliente Duplicado ==========");

        try {
            // Primer registro - exitoso
            logger.info("Registrando cliente con ID 123456...");
            negocio.registrarCliente("123456", "Juan Pérez", "Calle 123",
                                    "3001234567", "juan@example.com");
            logger.info("✓ Cliente registrado exitosamente");

            // Segundo registro - fallará por duplicado
            logger.info("\nIntentando registrar otro cliente con el mismo ID 123456...");
            negocio.registrarCliente("123456", "María García", "Av. 456",
                                    "3009876543", "maria@example.com");

        } catch (RecursoYaExisteException e) {
            // Este es el mensaje que ve el usuario
            logger.warn("⚠️ MENSAJE AL USUARIO: {}", e.getMessage());
            logger.info("\nEn lugar de mostrar error técnico de BD como:");
            logger.info("  'Duplicate entry '123456' for key 'personas.PRIMARY'");
            logger.info("\nEl usuario ve:");
            logger.info("  '{}'", e.getMessage());
        }
    }

    /**
     * Demuestra el manejo de recurso no encontrado.
     */
    public static void demostrarRecursoNoEncontrado(NegocioAlquiler negocio) {
        logger.info("\n========== DEMO: Recurso No Encontrado ==========");

        try {
            logger.info("Buscando prenda con referencia 'INEXISTENTE'...");
            negocio.buscarPrenda("INEXISTENTE")
                   .orElseThrow(() -> new RecursoNoEncontradoException("prenda", "INEXISTENTE"));

        } catch (RecursoNoEncontradoException e) {
            logger.info("ℹ️ MENSAJE AL USUARIO: {}", e.getMessage());
        }
    }

    /**
     * Demuestra el manejo de operación no permitida.
     */
    public static void demostrarOperacionNoPermitida() {
        logger.info("\n========== DEMO: Operación No Permitida ==========");

        try {
            logger.info("Intentando alquilar prenda que no está disponible...");
            throw new OperacionNoPermitidaException(
                "La prenda 'VD001' no está disponible para la fecha 2026-03-30"
            );

        } catch (OperacionNoPermitidaException e) {
            logger.warn("⚠️ MENSAJE AL USUARIO: {}", e.getMessage());
        }
    }

    /**
     * Muestra ejemplos de todos los tipos de mensajes.
     */
    public static void mostrarEjemplosMensajes() {
        logger.info("\n========== EJEMPLOS DE MENSAJES ==========\n");

        logger.info("1. CLIENTE DUPLICADO:");
        logger.info("   Mensaje: 'El número de identificación '123456' ya está registrado");
        logger.info("            en el sistema. Por favor, verifique el número de documento.'");
        logger.info("   Tipo: ⚠️ Warning (amarillo)\n");

        logger.info("2. EMPLEADO DUPLICADO:");
        logger.info("   Mensaje: 'El número de identificación '987654' ya está registrado");
        logger.info("            en el sistema. Por favor, verifique el número de documento.'");
        logger.info("   Tipo: ⚠️ Warning (amarillo)\n");

        logger.info("3. PRENDA DUPLICADA:");
        logger.info("   Mensaje: 'La referencia 'VD001' ya está registrada en el sistema.");
        logger.info("            Por favor, use una referencia diferente.'");
        logger.info("   Tipo: ⚠️ Warning (amarillo)\n");

        logger.info("4. RECURSO NO ENCONTRADO:");
        logger.info("   Mensaje: 'No se encontró el cliente con identificador '999999''");
        logger.info("   Tipo: ℹ️ Info (azul)\n");

        logger.info("5. OPERACIÓN NO PERMITIDA:");
        logger.info("   Mensaje: 'La prenda 'VD001' no está disponible para la fecha 2026-03-30'");
        logger.info("   Tipo: ⚠️ Warning (amarillo)\n");

        logger.info("6. ERROR GENERAL:");
        logger.info("   Mensaje: 'No se pudo registrar el cliente. Por favor, intente nuevamente.'");
        logger.info("   Tipo: ❌ Error (rojo)\n");
    }

    /**
     * Compara mensaje técnico vs mensaje amigable.
     */
    public static void compararMensajes() {
        logger.info("\n========== COMPARACIÓN: Antes vs Después ==========\n");

        logger.info("❌ ANTES (Mensaje Técnico):");
        logger.info("-----------------------------------------------------------");
        logger.info("Error al registrar cliente: could not execute statement");
        logger.info("[(conn=8) Duplicate entry '123456' for key 'personas.PRIMARY']");
        logger.info("[insert into personas (correo_electronico,direccion,nombre,");
        logger.info("telefono,tipo_persona,numero_identificacion) values");
        logger.info("(?,?,?,?,'CLIENTE',?)]; SQL [insert into personas...];");
        logger.info("constraint [personas.PRIMARY]");
        logger.info("-----------------------------------------------------------\n");

        logger.info("✅ DESPUÉS (Mensaje Amigable):");
        logger.info("-----------------------------------------------------------");
        logger.info("El número de identificación '123456' ya está registrado");
        logger.info("en el sistema. Por favor, verifique el número de documento.");
        logger.info("-----------------------------------------------------------\n");

        logger.info("BENEFICIOS:");
        logger.info("✓ Lenguaje comprensible para usuarios no técnicos");
        logger.info("✓ Indica específicamente cuál es el problema (ID duplicado)");
        logger.info("✓ Sugiere acción correctiva (verificar el número)");
        logger.info("✓ Sin jerga de base de datos o SQL");
        logger.info("✓ Mensaje corto y conciso");
    }

    /**
     * Método principal para ejecutar todas las demostraciones.
     */
    public static void main(String[] args) {
        logger.info("===============================================");
        logger.info("   DEMOSTRACIÓN DE MANEJO DE EXCEPCIONES");
        logger.info("   Sistema ClosetRent");
        logger.info("===============================================");

        // Mostrar comparación de mensajes
        compararMensajes();

        // Mostrar ejemplos de todos los mensajes
        mostrarEjemplosMensajes();

        logger.info("\n===============================================");
        logger.info("   Para ver las demos en acción:");
        logger.info("   1. Inicia la aplicación Spring Boot");
        logger.info("   2. Intenta registrar un cliente duplicado");
        logger.info("   3. Observa el mensaje amigable en la UI");
        logger.info("===============================================\n");

        // NOTA: Las demos que requieren NegocioAlquiler solo funcionan
        // cuando la aplicación Spring Boot está corriendo
        logger.info("NOTA: Las demos interactivas requieren que la aplicación");
        logger.info("      Spring Boot esté iniciada con una base de datos activa.");
    }
}
