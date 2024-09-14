package com.example.PruebaTecnica.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PruebaTecnica.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
