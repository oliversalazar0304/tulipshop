package com.tulipshop.repository;

import com.tulipshop.domain.Carrito;
import com.tulipshop.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioAndEstado(Usuario usuario, String estado);
}