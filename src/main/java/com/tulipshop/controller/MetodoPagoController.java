package com.tulipshop.controller;

import com.tulipshop.domain.MetodoPago;
import com.tulipshop.domain.Usuario;
import com.tulipshop.service.MetodoPagoService;
import com.tulipshop.util.SesionUsuario;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/metodos-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("metodos", metodoPagoService.listarPorUsuario(usuario));
        model.addAttribute("metodo", new MetodoPago());

        return "metodosPago";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute MetodoPago metodoPago, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        metodoPago.setUsuario(usuario);
        metodoPagoService.guardar(metodoPago);

        return "redirect:/metodos-pago";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        metodoPagoService.eliminar(id);
        return "redirect:/metodos-pago";
    }
}