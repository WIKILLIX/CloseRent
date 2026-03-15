package com.example.closetrent.service;

import com.example.closetrent.model.*;
import com.example.closetrent.model.decorator.IPrendaDecorator;
import com.example.closetrent.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Patrón Singleton y Facade: Clase principal del negocio de alquiler.
 * Proporciona una interfaz simplificada para todas las operaciones del negocio.
 *
 * En Spring, @Service con scope singleton garantiza una única instancia.
 */
@Service
public class NegocioAlquiler {

    private static NegocioAlquiler instancia;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PrendaRepository prendaRepository;

    @Autowired
    private ServicioAlquilerRepository servicioAlquilerRepository;

    @Autowired
    private LavanderiaService lavanderiaService;

    /**
     * Inicialización post-construcción para el patrón Singleton.
     */
    @PostConstruct
    public void init() {
        instancia = this;
    }

    /**
     * Obtiene la instancia única del negocio (Patrón Singleton).
     */
    public static NegocioAlquiler getInstancia() {
        return instancia;
    }

    // ==================== GESTIÓN DE CLIENTES ====================

    /**
     * Registra un nuevo cliente.
     */
    @Transactional
    public Cliente registrarCliente(String numeroId, String nombre, String direccion,
                                   String telefono, String correo) {
        Cliente cliente = new Cliente(numeroId, nombre, direccion, telefono, correo);
        return clienteRepository.save(cliente);
    }

    /**
     * Busca un cliente por número de identificación.
     */
    public Optional<Cliente> buscarCliente(String numeroId) {
        return clienteRepository.findById(numeroId);
    }

    /**
     * Lista todos los clientes.
     */
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // ==================== GESTIÓN DE EMPLEADOS ====================

    /**
     * Registra un nuevo empleado.
     */
    @Transactional
    public Empleado registrarEmpleado(String numeroId, String nombre, String direccion,
                                     String telefono, String correo, String cargo) {
        Empleado empleado = new Empleado(numeroId, nombre, direccion, telefono, correo, cargo);
        return empleadoRepository.save(empleado);
    }

    /**
     * Busca un empleado por número de identificación.
     */
    public Optional<Empleado> buscarEmpleado(String numeroId) {
        return empleadoRepository.findById(numeroId);
    }

    /**
     * Lista todos los empleados.
     */
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    // ==================== GESTIÓN DE PRENDAS ====================

    /**
     * Registra una nueva prenda.
     */
    @Transactional
    public Prenda registrarPrenda(Prenda prenda) {
        return prendaRepository.save(prenda);
    }

    /**
     * Busca una prenda por referencia.
     */
    public Optional<Prenda> buscarPrenda(String referencia) {
        return prendaRepository.findById(referencia);
    }

    /**
     * Lista todas las prendas.
     */
    public List<Prenda> listarPrendas() {
        return prendaRepository.findAll();
    }

    /**
     * Consulta prendas por talla.
     */
    public List<Prenda> consultarPrendasPorTalla(String talla) {
        return prendaRepository.findByTalla(talla);
    }

    /**
     * Verifica si una prenda está disponible para una fecha.
     */
    public boolean verificarDisponibilidadPrenda(String referenciaPrenda, LocalDate fecha) {
        Optional<Prenda> prendaOpt = prendaRepository.findById(referenciaPrenda);
        if (prendaOpt.isEmpty() || !prendaOpt.get().getDisponible()) {
            return false;
        }

        // Verificar si hay algún servicio que use esta prenda en la fecha
        List<ServicioAlquiler> servicios = servicioAlquilerRepository.findByFechaAlquiler(fecha);
        for (ServicioAlquiler servicio : servicios) {
            for (Prenda prenda : servicio.getPrendas()) {
                if (prenda.getReferencia().equals(referenciaPrenda)) {
                    return false;
                }
            }
        }
        return true;
    }

    // ==================== GESTIÓN DE SERVICIOS DE ALQUILER ====================

    /**
     * Registra un nuevo servicio de alquiler.
     */
    @Transactional
    public ServicioAlquiler registrarServicioAlquiler(String clienteId, String empleadoId,
                                                     List<String> referenciasPrendas,
                                                     LocalDate fechaAlquiler)
            throws Exception {

        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new Exception("Cliente no encontrado: " + clienteId));

        // Validar que el empleado existe
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new Exception("Empleado no encontrado: " + empleadoId));

        // Validar y obtener las prendas
        List<Prenda> prendas = new java.util.ArrayList<>();
        for (String ref : referenciasPrendas) {
            Prenda prenda = prendaRepository.findById(ref)
                    .orElseThrow(() -> new Exception("Prenda no encontrada: " + ref));

            // Verificar disponibilidad
            if (!verificarDisponibilidadPrenda(ref, fechaAlquiler)) {
                throw new Exception("Prenda no disponible para la fecha: " + ref);
            }

            prendas.add(prenda);
        }

        // Crear el servicio
        ServicioAlquiler servicio = new ServicioAlquiler(
                LocalDate.now(),
                fechaAlquiler,
                empleado,
                cliente,
                prendas
        );

        return servicioAlquilerRepository.save(servicio);
    }

    /**
     * Consulta un servicio de alquiler por número.
     */
    public Optional<ServicioAlquiler> consultarServicioAlquiler(Long numeroServicio) {
        return servicioAlquilerRepository.findById(numeroServicio);
    }

    /**
     * Consulta servicios por cliente.
     */
    public List<ServicioAlquiler> consultarServiciosPorCliente(String clienteId) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            return List.of();
        }
        return servicioAlquilerRepository.findServiciosVigentesByCliente(
                clienteOpt.get(), LocalDate.now());
    }

    /**
     * Consulta servicios por fecha de alquiler.
     */
    public List<ServicioAlquiler> consultarServiciosPorFecha(LocalDate fecha) {
        return servicioAlquilerRepository.findByFechaAlquiler(fecha);
    }

    // ==================== GESTIÓN DE LAVANDERÍA ====================

    /**
     * Registra una prenda para envío a lavandería.
     */
    public void registrarPrendaParaLavanderia(String referenciaPrenda, boolean esPrioridad,
                                             String motivo) throws Exception {
        Prenda prenda = prendaRepository.findById(referenciaPrenda)
                .orElseThrow(() -> new Exception("Prenda no encontrada: " + referenciaPrenda));

        if (esPrioridad) {
            lavanderiaService.registrarPrendaPrioridad(prenda, motivo);
        } else {
            lavanderiaService.registrarPrendaNormal(prenda);
        }
    }

    /**
     * Obtiene el listado de prendas para lavandería.
     */
    public List<IPrendaDecorator> obtenerListadoLavanderia() {
        return lavanderiaService.obtenerListadoCompleto();
    }

    /**
     * Envía prendas a lavandería.
     */
    public List<IPrendaDecorator> enviarPrendasALavanderia(int cantidad) {
        return lavanderiaService.enviarPrendasALavanderia(cantidad);
    }
}
