package com.tulipshop.controller;

import com.tulipshop.domain.Categoria;
import com.tulipshop.domain.Usuario;
import com.tulipshop.service.CategoriaService;
import com.tulipshop.service.ProductoService;
import com.tulipshop.util.SesionUsuario;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping("/catalogo")
    public String catalogo(@RequestParam(required = false) Long categoriaId,
                           Model model,
                           HttpSession session) {

        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("categorias", categoriaService.listarTodas());

        if (categoriaId != null) {
            Categoria categoria = categoriaService.buscarPorId(categoriaId).orElseThrow();
            model.addAttribute("productos", productoService.listarPorCategoria(categoria));
        } else {
            model.addAttribute("productos", productoService.listarTodos());
        }

        return "catalogo";
    }

    @GetMapping("/producto/{id}")
    public String detalle(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("producto", productoService.buscarPorId(id).orElseThrow());

        return "productoDetalle";
    }
}