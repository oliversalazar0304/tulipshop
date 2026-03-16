package com.tulipshop.util;

import com.tulipshop.domain.Usuario;
import jakarta.servlet.http.HttpSession;

public class SesionUsuario {

    public static Usuario getUsuarioLogueado(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    public static boolean estaLogueado(HttpSession session) {
        return getUsuarioLogueado(session) != null;
    }

    public static boolean esAdmin(HttpSession session) {
        Usuario usuario = getUsuarioLogueado(session);
        return usuario != null
                && usuario.getRol() != null
                && "ADMIN".equals(usuario.getRol().getNombre());
    }
}
