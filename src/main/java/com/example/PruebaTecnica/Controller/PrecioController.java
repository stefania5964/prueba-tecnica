package com.example.PruebaTecnica.Controller;

import com.example.PruebaTecnica.Repository.PrecioRepository;
import com.example.PruebaTecnica.Repository.ProductoRepository;
import com.example.PruebaTecnica.exceptions.ResourceNotFoundException;
import com.example.PruebaTecnica.model.Precio;
import com.example.PruebaTecnica.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/precios")
public class PrecioController {

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping
    public List<Precio> listarPrecios() {
        return precioRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Precio> obtenerPrecio(@PathVariable Long id) {
        Precio precio = precioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio con id " + id + " no encontrado"));
        return ResponseEntity.ok(precio);
    }


    @PostMapping("/producto/{codigoProducto}")
    public ResponseEntity<Precio> crearPrecio(@PathVariable Long codigoProducto, @RequestBody Precio nuevoPrecio) {
        Producto producto = productoRepository.findById(codigoProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con código " + codigoProducto + " no encontrado"));

        nuevoPrecio.setProducto(producto);
        Precio precioGuardado = precioRepository.save(nuevoPrecio);
        return ResponseEntity.ok(precioGuardado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Precio> actualizarPrecio(@PathVariable Long id, @RequestBody Precio precioActualizado) {
        Precio precioExistente = precioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio con id " + id + " no encontrado"));


        precioExistente.setValor(precioActualizado.getValor());
        precioExistente.setMoneda(precioActualizado.getMoneda());
        precioExistente.setProducto(precioActualizado.getProducto());

        Precio precioGuardado = precioRepository.save(precioExistente);
        return ResponseEntity.ok(precioGuardado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrecio(@PathVariable Long id) {
        Precio precio = precioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio con id " + id + " no encontrado"));

        precioRepository.delete(precio);
        return ResponseEntity.ok().build();
    }
}