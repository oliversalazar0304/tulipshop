package com.tulipshop.controller;

import com.tulipshop.domain.Producto;
import com.tulipshop.domain.Usuario;
import com.tulipshop.service.CarritoService;
import com.tulipshop.service.MetodoPagoService;
import com.tulipshop.service.ProductoService;
import com.tulipshop.util.SesionUsuario;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final MetodoPagoService metodoPagoService;

    @PostMapping("/agregar/{productoId}")
    public String agregar(@PathVariable Long productoId,
                          @RequestParam(defaultValue = "1") int cantidad,
                          HttpSession session) {

        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        Producto producto = productoService.buscarPorId(productoId).orElseThrow();
        carritoService.agregarProducto(usuario, producto, cantidad);

        return "redirect:/carrito";
    }

    @GetMapping
    public String verCarrito(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        BigDecimal subtotal = carritoService.calcularSubtotal(usuario);
        BigDecimal impuesto = subtotal.multiply(new BigDecimal("0.13"));
        BigDecimal envio = new BigDecimal("2500.00");
        BigDecimal total = subtotal.add(impuesto).add(envio);

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("detalles", carritoService.listarDetalle(usuario));
        model.addAttribute("metodos", metodoPagoService.listarPorUsuario(usuario));
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("impuesto", impuesto);
        model.addAttribute("envio", envio);
        model.addAttribute("total", total);

        return "carrito";
    }

    @PostMapping("/actualizar/{detalleId}")
    public String actualizar(@PathVariable Long detalleId,
                             @RequestParam int cantidad,
                             HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        carritoService.actualizarCantidad(detalleId, cantidad);
        return "redirect:/carrito";
    }

    @GetMapping("/eliminar/{detalleId}")
    public String eliminar(@PathVariable Long detalleId, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        carritoService.eliminarDetalle(detalleId);
        return "redirect:/carrito";
    }
}
