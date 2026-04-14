package com.tulipshop.repository;

import com.tulipshop.domain.Carrito;
import com.tulipshop.domain.CarritoDetalle;
import com.tulipshop.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoDetalleRepository extends JpaRepository<CarritoDetalle, Long> {
    List<CarritoDetalle> findByCarrito(Carrito carrito);
    Optional<CarritoDetalle> findByCarritoAndProducto(Carrito carrito, Producto producto);
    List<CarritoDetalle> findByProducto(Producto producto); 
}
