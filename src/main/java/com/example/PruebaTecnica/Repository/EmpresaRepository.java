package com.example.PruebaTecnica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PruebaTecnica.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
