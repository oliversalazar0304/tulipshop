package com.tulipshop.service;

import com.tulipshop.domain.Rol;
import com.tulipshop.domain.Usuario;
import com.tulipshop.repository.RolRepository;
import com.tulipshop.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public Usuario registrar(Usuario usuario) {
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

        usuario.setRol(rolCliente);
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String correo, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
            return usuarioOpt.get();
        }

        return null;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
