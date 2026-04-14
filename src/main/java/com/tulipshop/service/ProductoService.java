package com.tulipshop.service;

import com.tulipshop.domain.Categoria;
import com.tulipshop.domain.Producto;
import com.tulipshop.repository.CarritoDetalleRepository;
import com.tulipshop.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CarritoDetalleRepository carritoDetalleRepository; 

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        carritoDetalleRepository.deleteAll(           
            carritoDetalleRepository.findByProducto(producto)
        );
        productoRepository.deleteById(id);
    }

    public List<Producto> listarPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }
}
