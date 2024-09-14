package com.example.PruebaTecnica.Controller;

import com.example.PruebaTecnica.Repository.OrdenRepository;
import com.example.PruebaTecnica.model.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    @PostMapping("/crear")
    public Orden crearOrden(@RequestBody Orden orden) {
        return ordenRepository.save(orden);
    }

    @GetMapping("/listar")
    public List<Orden> listarOrdenes() {
        return ordenRepository.findAll();
    }

    @GetMapping("/cliente/{id}")
    public List<Orden> obtenerOrdenesPorCliente(@PathVariable Long id) {
        return ordenRepository.findByCliente_Id(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarOrden(@PathVariable Long id) {
        ordenRepository.deleteById(id);
        return "Orden eliminada con éxito.";
    }
}
