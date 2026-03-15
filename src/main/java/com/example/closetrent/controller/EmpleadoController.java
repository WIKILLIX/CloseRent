package com.example.closetrent.controller;

import com.example.closetrent.model.Empleado;
import com.example.closetrent.service.NegocioAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para gestión de empleados.
 */
@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private NegocioAlquiler negocioAlquiler;

    @GetMapping
    public String listarEmpleados(Model model) {
        model.addAttribute("empleados", negocioAlquiler.listarEmpleados());
        return "empleados/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleado empleado,
                                 RedirectAttributes redirectAttributes) {
        try {
            negocioAlquiler.registrarEmpleado(
                    empleado.getNumeroIdentificacion(),
                    empleado.getNombre(),
                    empleado.getDireccion(),
                    empleado.getTelefono(),
                    empleado.getCorreoElectronico(),
                    empleado.getCargo()
            );
            redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al registrar empleado: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/empleados";
    }
}
