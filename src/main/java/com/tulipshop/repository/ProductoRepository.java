package com.tulipshop.repository;

import com.tulipshop.domain.Categoria;
import com.tulipshop.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
