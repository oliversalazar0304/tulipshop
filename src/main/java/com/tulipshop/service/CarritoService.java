package com.tulipshop.service;

import com.tulipshop.domain.Carrito;
import com.tulipshop.domain.CarritoDetalle;
import com.tulipshop.domain.Producto;
import com.tulipshop.domain.Usuario;
import com.tulipshop.repository.CarritoDetalleRepository;
import com.tulipshop.repository.CarritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoDetalleRepository carritoDetalleRepository;

    public Carrito obtenerCarritoActivo(Usuario usuario) {
        return carritoRepository.findByUsuarioAndEstado(usuario, "ACTIVO")
                .orElseGet(() -> {
                    Carrito carrito = new Carrito(usuario, LocalDateTime.now(), "ACTIVO");
                    return carritoRepository.save(carrito);
                });
    }

    public void agregarProducto(Usuario usuario, Producto producto, int cantidad) {
        Carrito carrito = obtenerCarritoActivo(usuario);

        CarritoDetalle detalle = carritoDetalleRepository.findByCarritoAndProducto(carrito, producto)
                .orElseGet(() -> new CarritoDetalle(
                        carrito,
                        producto,
                        0,
                        producto.getPrecio(),
                        BigDecimal.ZERO
                ));

        detalle.setCantidad(detalle.getCantidad() + cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())));

        carritoDetalleRepository.save(detalle);
    }

    public List<CarritoDetalle> listarDetalle(Usuario usuario) {
        Carrito carrito = obtenerCarritoActivo(usuario);
        return carritoDetalleRepository.findByCarrito(carrito);
    }

    public void actualizarCantidad(Long detalleId, int cantidad) {
        CarritoDetalle detalle = carritoDetalleRepository.findById(detalleId).orElseThrow();
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad)));
        carritoDetalleRepository.save(detalle);
    }

    public void eliminarDetalle(Long detalleId) {
        carritoDetalleRepository.deleteById(detalleId);
    }

    public BigDecimal calcularSubtotal(Usuario usuario) {
        return listarDetalle(usuario).stream()
                .map(CarritoDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
