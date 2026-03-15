package com.example.closetrent.controller;

import com.example.closetrent.model.decorator.IPrendaDecorator;
import com.example.closetrent.service.NegocioAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador para gestión de lavandería.
 * Utiliza el patrón Decorator para manejar prioridades.
 */
@Controller
@RequestMapping("/lavanderia")
public class LavanderiaController {

    @Autowired
    private NegocioAlquiler negocioAlquiler;

    @GetMapping
    public String menuLavanderia() {
        return "lavanderia/menu";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("prendas", negocioAlquiler.listarPrendas());
        return "lavanderia/registrar";
    }

    @PostMapping("/registrar")
    public String registrarPrenda(@RequestParam String referenciaPrenda,
                                 @RequestParam(defaultValue = "false") boolean esPrioridad,
                                 @RequestParam(required = false) String motivo,
                                 RedirectAttributes redirectAttributes) {
        try {
            String motivoFinal = esPrioridad ?
                    (motivo != null && !motivo.isEmpty() ? motivo : "Prioridad administrativa") :
                    "Normal";

            negocioAlquiler.registrarPrendaParaLavanderia(
                    referenciaPrenda, esPrioridad, motivoFinal
            );

            redirectAttributes.addFlashAttribute("mensaje",
                    "Prenda registrada para lavandería " + (esPrioridad ? "con PRIORIDAD" : ""));
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Error al registrar prenda: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/lavanderia";
    }

    @GetMapping("/listado")
    public String verListado(Model model) {
        List<IPrendaDecorator> listado = negocioAlquiler.obtenerListadoLavanderia();
        model.addAttribute("prendas", listado);
        model.addAttribute("total", listado.size());
        return "lavanderia/listado";
    }

    @PostMapping("/enviar")
    public String enviarPrendas(@RequestParam int cantidad,
                               RedirectAttributes redirectAttributes) {
        try {
            List<IPrendaDecorator> prendasEnviadas =
                    negocioAlquiler.enviarPrendasALavanderia(cantidad);

            redirectAttributes.addFlashAttribute("mensaje",
                    "Se enviaron " + prendasEnviadas.size() + " prendas a lavandería");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            redirectAttributes.addFlashAttribute("prendasEnviadas", prendasEnviadas);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Error al enviar prendas: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/lavanderia/listado";
    }
}
