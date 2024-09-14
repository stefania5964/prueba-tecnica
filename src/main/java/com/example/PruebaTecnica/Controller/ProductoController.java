package com.example.PruebaTecnica.Controller;
import com.example.PruebaTecnica.Repository.ProductoRepository;
import com.example.PruebaTecnica.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.PruebaTecnica.model.Producto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto productoGuardado = productoRepository.save(producto);
        return ResponseEntity.ok(productoGuardado);
    }
    @PutMapping("/{codigo}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long codigo, @RequestBody Producto productoActualizado) {
        Optional<Producto> productoOptional = productoRepository.findById(codigo);

        if (productoOptional.isPresent()) {
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setCaracteristicas(productoActualizado.getCaracteristicas());
            productoExistente.setPrecios(productoActualizado.getPrecios()); // Agregar o actualizar precio
            productoExistente.setEmpresa(productoActualizado.getEmpresa());
            productoExistente.setCategorias(productoActualizado.getCategorias());

            Producto productoActualizadoGuardado = productoRepository.save(productoExistente);
            return ResponseEntity.ok(productoActualizadoGuardado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long codigo) {
        Producto producto = productoRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con código " + codigo + " no encontrado"));

        productoRepository.delete(producto);
        return ResponseEntity.ok().build();
    }
}
