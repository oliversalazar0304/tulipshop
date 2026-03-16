package com.tulipshop.repository;

import com.tulipshop.domain.MetodoPago;
import com.tulipshop.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByUsuario(Usuario usuario);
}