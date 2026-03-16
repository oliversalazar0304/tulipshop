package com.tulipshop.controller;

import com.tulipshop.domain.Usuario;
import com.tulipshop.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        Usuario usuario = usuarioService.login(correo, password);

        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuarioLogueado", usuario);
        return "redirect:/";
    }

    @GetMapping("/registro")
    public String registro(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/";
        }

        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String guardarRegistro(@ModelAttribute Usuario usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
