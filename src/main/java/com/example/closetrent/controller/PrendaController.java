package com.example.closetrent.controller;

import com.example.closetrent.model.*;
import com.example.closetrent.model.TrajeCaballero.AccesorioCuello;
import com.example.closetrent.model.TrajeCaballero.TipoTraje;
import com.example.closetrent.model.factory.*;
import com.example.closetrent.service.NegocioAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para gestión de prendas.
 * Utiliza el patrón Factory Method para crear prendas.
 */
@Controller
@RequestMapping("/prendas")
public class PrendaController {

    @Autowired
    private NegocioAlquiler negocioAlquiler;

    @GetMapping
    public String listarPrendas(Model model) {
        model.addAttribute("prendas", negocioAlquiler.listarPrendas());
        return "prendas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("tiposTraje", TipoTraje.values());
        model.addAttribute("accesoriosCuello", AccesorioCuello.values());
        return "prendas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPrenda(@RequestParam String tipoPrenda,
                               @RequestParam String referencia,
                               @RequestParam String color,
                               @RequestParam String marca,
                               @RequestParam String talla,
                               @RequestParam Double valorAlquiler,
                               // VestidoDama
                               @RequestParam(required = false) Boolean tienePedreria,
                               @RequestParam(required = false) Boolean esLargo,
                               @RequestParam(required = false) Integer cantidadPiezas,
                               // TrajeCaballero
                               @RequestParam(required = false) String tipoTraje,
                               @RequestParam(required = false) String accesorioCuello,
                               // Disfraz
                               @RequestParam(required = false) String nombreDisfraz,
                               RedirectAttributes redirectAttributes) {
        try {
            Prenda prenda;
            PrendaFactory factory;

            switch (tipoPrenda) {
                case "VESTIDO_DAMA":
                    factory = new VestidoDamaFactory(tienePedreria, esLargo, cantidadPiezas);
                    prenda = factory.crearPrenda(referencia, color, marca, talla, valorAlquiler);
                    break;
                case "TRAJE_CABALLERO":
                    factory = new TrajeCaballeroFactory(
                            TipoTraje.valueOf(tipoTraje),
                            AccesorioCuello.valueOf(accesorioCuello)
                    );
                    prenda = factory.crearPrenda(referencia, color, marca, talla, valorAlquiler);
                    break;
                case "DISFRAZ":
                    factory = new DisfrazFactory(nombreDisfraz);
                    prenda = factory.crearPrenda(referencia, color, marca, talla, valorAlquiler);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de prenda no válido");
            }

            negocioAlquiler.registrarPrenda(prenda);
            redirectAttributes.addFlashAttribute("mensaje", "Prenda registrada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (com.example.closetrent.exception.RecursoYaExisteException e) {
            // Manejo específico para prendas duplicadas
            redirectAttributes.addFlashAttribute("mensaje",
                    "La referencia '" + referencia + "' ya está registrada en el sistema. " +
                    "Por favor, use una referencia diferente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
        } catch (IllegalArgumentException e) {
            // Manejo de argumentos inválidos
            redirectAttributes.addFlashAttribute("mensaje",
                    "Datos inválidos: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
        } catch (Exception e) {
            // Manejo de otros errores
            redirectAttributes.addFlashAttribute("mensaje",
                    "No se pudo registrar la prenda. Por favor, verifique los datos e intente nuevamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/prendas";
    }

    @GetMapping("/consultar-por-talla")
    public String consultarPorTalla(@RequestParam String talla, Model model) {
        model.addAttribute("talla", talla);
        model.addAttribute("prendas", negocioAlquiler.consultarPrendasPorTalla(talla));
        return "prendas/por-talla";
    }
}
