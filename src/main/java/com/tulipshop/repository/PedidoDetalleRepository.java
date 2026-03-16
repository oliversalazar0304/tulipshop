package com.tulipshop.repository;

import com.tulipshop.domain.Pedido;
import com.tulipshop.domain.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
    List<PedidoDetalle> findByPedido(Pedido pedido);
}
