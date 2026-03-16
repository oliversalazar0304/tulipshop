package com.tulipshop.controller;

import com.tulipshop.domain.Categoria;
import com.tulipshop.domain.Producto;
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
@RequestMapping("/admin")
public class AdminController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    private boolean esAdmin(Usuario usuario) {
        return usuario != null
                && usuario.getRol() != null
                && "ADMIN".equals(usuario.getRol().getNombre());
    }

    @GetMapping("/productos")
    public String adminProductos(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("productos", productoService.listarTodos());
        return "adminProductos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProducto(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "adminProductoForm";
    }

    @GetMapping("/productos/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("producto", productoService.buscarPorId(id).orElseThrow());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "adminProductoForm";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam Long categoriaId,
                                  HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        Categoria categoria = categoriaService.buscarPorId(categoriaId).orElseThrow();
        producto.setCategoria(categoria);
        productoService.guardar(producto);

        return "redirect:/admin/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }

    @GetMapping("/categorias")
    public String adminCategorias(Model model, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("categoria", new Categoria());
        return "adminCategorias";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        categoriaService.guardar(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, HttpSession session) {
        Usuario usuario = SesionUsuario.getUsuarioLogueado(session);
        if (!esAdmin(usuario)) {
            return "redirect:/";
        }

        categoriaService.eliminar(id);
        return "redirect:/admin/categorias";
    }
}
