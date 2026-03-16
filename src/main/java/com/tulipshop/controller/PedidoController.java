package com.tulipshop.controller;

import com.tulipshop.domain.MetodoPago;
import com.tulipshop.domain.Usuario;
import com.tulipshop.service.MetodoPagoService;
import com.tulipshop.service.PedidoService;
import com.tulipshop.util.SesionUsuario;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("pedidos", pedidoService.listarPorUsuario(usuario));

        return "misPedidos";
    }

    @PostMapping("/confirmar")
    public String confirmar(@RequestParam Long metodoPagoId,
                            @RequestParam String fechaEntrega,
                            HttpSession session) {

        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        MetodoPago metodoPago = metodoPagoService.buscarPorId(metodoPagoId).orElseThrow();
        pedidoService.crearPedido(usuario, metodoPago, LocalDate.parse(fechaEntrega));

        return "redirect:/pedidos";
    }
}
