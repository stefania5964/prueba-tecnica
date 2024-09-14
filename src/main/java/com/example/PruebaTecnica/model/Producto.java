package com.example.PruebaTecnica.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(name = "nombre", length = 100)
    private String nombre;
    @Column(name = "caracteristicas")
    private String caracteristicas;

    @OneToMany(mappedBy = "producto")
    private List<Precio> precios;
    @ManyToOne
    @JoinColumn(name = "empresa_nit")
    private Empresa empresa;
    @ManyToMany
    @JoinTable(
            name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_codigo"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;
}
