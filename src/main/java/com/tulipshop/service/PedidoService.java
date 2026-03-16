package com.tulipshop.service;

import com.tulipshop.domain.Carrito;
import com.tulipshop.domain.CarritoDetalle;
import com.tulipshop.domain.MetodoPago;
import com.tulipshop.domain.Pedido;
import com.tulipshop.domain.PedidoDetalle;
import com.tulipshop.domain.Usuario;
import com.tulipshop.repository.CarritoDetalleRepository;
import com.tulipshop.repository.CarritoRepository;
import com.tulipshop.repository.PedidoDetalleRepository;
import com.tulipshop.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final CarritoService carritoService;
    private final CarritoRepository carritoRepository;
    private final CarritoDetalleRepository carritoDetalleRepository;

    public Pedido crearPedido(Usuario usuario, MetodoPago metodoPago, LocalDate fechaEntrega) {
        BigDecimal subtotal = carritoService.calcularSubtotal(usuario);
        BigDecimal impuesto = subtotal.multiply(new BigDecimal("0.13"));
        BigDecimal envio = new BigDecimal("2500.00");
        BigDecimal total = subtotal.add(impuesto).add(envio);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setMetodoPago(metodoPago);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setFechaEntrega(fechaEntrega);
        pedido.setSubtotal(subtotal);
        pedido.setImpuesto(impuesto);
        pedido.setEnvio(envio);
        pedido.setTotal(total);
        pedido.setEstado("CONFIRMADO");

        pedido = pedidoRepository.save(pedido);

        Carrito carrito = carritoService.obtenerCarritoActivo(usuario);
        List<CarritoDetalle> detalles = carritoDetalleRepository.findByCarrito(carrito);

        for (CarritoDetalle d : detalles) {
            PedidoDetalle pd = new PedidoDetalle();
            pd.setPedido(pedido);
            pd.setProducto(d.getProducto());
            pd.setCantidad(d.getCantidad());
            pd.setPrecioUnitario(d.getPrecioUnitario());
            pd.setSubtotal(d.getSubtotal());
            pedidoDetalleRepository.save(pd);
        }

        carrito.setEstado("CERRADO");
        carritoRepository.save(carrito);

        return pedido;
    }

    public List<Pedido> listarPorUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuario(usuario);
    }
}