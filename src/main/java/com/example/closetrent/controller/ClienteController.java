package com.example.closetrent.controller;

import com.example.closetrent.exception.RecursoYaExisteException;
import com.example.closetrent.model.Cliente;
import com.example.closetrent.service.NegocioAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para gestión de clientes.
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private NegocioAlquiler negocioAlquiler;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", negocioAlquiler.listarClientes());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente,
                                RedirectAttributes redirectAttributes) {
        try {
            negocioAlquiler.registrarCliente(
                    cliente.getNumeroIdentificacion(),
                    cliente.getNombre(),
                    cliente.getDireccion(),
                    cliente.getTelefono(),
                    cliente.getCorreoElectronico()
            );
            redirectAttributes.addFlashAttribute("mensaje", "Cliente registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (RecursoYaExisteException e) {
            // Manejo específico para clientes duplicados
            redirectAttributes.addFlashAttribute("mensaje",
                    "El número de identificación '" + cliente.getNumeroIdentificacion() +
                    "' ya está registrado en el sistema. Por favor, verifique el número de documento.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
        } catch (Exception e) {
            // Manejo de otros errores
            redirectAttributes.addFlashAttribute("mensaje",
                    "No se pudo registrar el cliente. Por favor, intente nuevamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/consultar")
    public String consultarCliente(@RequestParam String numeroId, Model model) {
        return negocioAlquiler.buscarCliente(numeroId)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    model.addAttribute("servicios",
                            negocioAlquiler.consultarServiciosPorCliente(numeroId));
                    return "clientes/detalle";
                })
                .orElse("redirect:/clientes");
    }
}
