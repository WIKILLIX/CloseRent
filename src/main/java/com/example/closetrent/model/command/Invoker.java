package com.example.closetrent.model.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Command: Invoker que ejecuta comandos.
 * Mantiene un historial de comandos ejecutados.
 */
@Component
public class Invoker {

    private static final Logger logger = LoggerFactory.getLogger(Invoker.class);

    private final List<Command> historialComandos = new ArrayList<>();

    /**
     * Establece y ejecuta un comando.
     *
     * @param command El comando a ejecutar
     * @throws Exception Si ocurre un error durante la ejecución
     */
    public void ejecutarComando(Command command) throws Exception {
        logger.info("Ejecutando comando: {}", command.getDescripcion());

        try {
            command.ejecutar();
            historialComandos.add(command);
            logger.info("Comando ejecutado exitosamente: {}", command.getDescripcion());
        } catch (Exception e) {
            logger.error("Error al ejecutar comando: {}", command.getDescripcion(), e);
            throw e;
        }
    }

    /**
     * Obtiene el historial de comandos ejecutados.
     *
     * @return Lista de comandos ejecutados
     */
    public List<Command> obtenerHistorial() {
        return new ArrayList<>(historialComandos);
    }

    /**
     * Limpia el historial de comandos.
     */
    public void limpiarHistorial() {
        historialComandos.clear();
        logger.info("Historial de comandos limpiado");
    }

    /**
     * Obtiene el último comando ejecutado.
     *
     * @return El último comando o null si no hay historial
     */
    public Command obtenerUltimoComando() {
        if (historialComandos.isEmpty()) {
            return null;
        }
        return historialComandos.get(historialComandos.size() - 1);
    }
}
