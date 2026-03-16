package com.tulipshop.controller;

import com.tulipshop.domain.Usuario;
import com.tulipshop.service.ProductoService;
import com.tulipshop.util.SesionUsuario;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductoService productoService;

    @GetMapping("/")
    public String inicio(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("productos", productoService.listarTodos());

        return "index";
    }
}