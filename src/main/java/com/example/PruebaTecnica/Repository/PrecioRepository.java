package com.example.PruebaTecnica.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PruebaTecnica.model.Precio;

public interface PrecioRepository extends JpaRepository<Precio, Long> {
}
