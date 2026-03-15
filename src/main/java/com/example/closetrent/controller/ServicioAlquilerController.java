package com.example.closetrent.controller;

import com.example.closetrent.service.NegocioAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestión de servicios de alquiler.
 */
@Controller
@RequestMapping("/servicios")
public class ServicioAlquilerController {

    @Autowired
    private NegocioAlquiler negocioAlquiler;

    @GetMapping
    public String listarServicios(Model model) {
        return "servicios/menu";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("clientes", negocioAlquiler.listarClientes());
        model.addAttribute("empleados", negocioAlquiler.listarEmpleados());
        model.addAttribute("prendas", negocioAlquiler.listarPrendas());
        return "servicios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarServicio(@RequestParam String clienteId,
                                 @RequestParam String empleadoId,
                                 @RequestParam String referenciasPrendas,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 LocalDate fechaAlquiler,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Convertir las referencias separadas por comas a lista
            List<String> referencias = Arrays.stream(referenciasPrendas.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            var servicio = negocioAlquiler.registrarServicioAlquiler(
                    clienteId, empleadoId, referencias, fechaAlquiler
            );

            redirectAttributes.addFlashAttribute("mensaje",
                    "Servicio de alquiler #" + servicio.getNumeroServicio() + " registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Error al registrar servicio: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/servicios";
    }

    @GetMapping("/consultar")
    public String consultarServicio(@RequestParam Long numeroServicio, Model model,
                                   RedirectAttributes redirectAttributes) {
        return negocioAlquiler.consultarServicioAlquiler(numeroServicio)
                .map(servicio -> {
                    model.addAttribute("servicio", servicio);
                    return "servicios/detalle";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensaje", "Servicio no encontrado");
                    redirectAttributes.addFlashAttribute("tipoMensaje", "error");
                    return "redirect:/servicios";
                });
    }

    @GetMapping("/consultar-por-cliente")
    public String consultarPorCliente(@RequestParam String clienteId, Model model) {
        return negocioAlquiler.buscarCliente(clienteId)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    model.addAttribute("servicios",
                            negocioAlquiler.consultarServiciosPorCliente(clienteId));
                    return "servicios/por-cliente";
                })
                .orElse("redirect:/servicios");
    }

    @GetMapping("/consultar-por-fecha")
    public String consultarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {
        model.addAttribute("fecha", fecha);
        model.addAttribute("servicios", negocioAlquiler.consultarServiciosPorFecha(fecha));
        return "servicios/por-fecha";
    }
}
