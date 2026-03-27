package com.example.closetrent.model.command;

import com.example.closetrent.model.Prenda;
import com.example.closetrent.service.NegocioAlquiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Comando concreto para devolver una prenda.
 * Encapsula la acción de marcar una prenda como disponible.
 */
public class DevolverPrendaCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DevolverPrendaCommand.class);

    private final NegocioAlquiler negocioAlquiler;
    private final String referenciaPrenda;

    /**
     * Constructor del comando.
     *
     * @param negocioAlquiler Instancia del negocio
     * @param referenciaPrenda Referencia de la prenda a devolver
     */
    public DevolverPrendaCommand(NegocioAlquiler negocioAlquiler, String referenciaPrenda) {
        this.negocioAlquiler = negocioAlquiler;
        this.referenciaPrenda = referenciaPrenda;
    }

    @Override
    public void ejecutar() throws Exception {
        Prenda prenda = negocioAlquiler.buscarPrenda(referenciaPrenda)
                .orElseThrow(() -> new Exception("Prenda no encontrada: " + referenciaPrenda));

        if (prenda.getDisponible()) {
            throw new Exception("La prenda ya está disponible: " + referenciaPrenda);
        }

        // Cambiar estado y notificar observadores
        prenda.cambiarEstado(true);

        logger.info("Prenda {} devuelta exitosamente", referenciaPrenda);
    }

    @Override
    public String getDescripcion() {
        return "Devolver prenda: " + referenciaPrenda;
    }
}
