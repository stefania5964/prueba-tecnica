package com.example.PruebaTecnica.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PruebaTecnica.model.Orden;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByCliente_Id(Long clienteId);

}