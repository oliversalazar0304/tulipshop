package com.tulipshop.service;

import com.tulipshop.domain.MetodoPago;
import com.tulipshop.domain.Usuario;
import com.tulipshop.repository.MetodoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> listarPorUsuario(Usuario usuario) {
        return metodoPagoRepository.findByUsuario(usuario);
    }

    public MetodoPago guardar(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public Optional<MetodoPago> buscarPorId(Long id) {
        return metodoPagoRepository.findById(id);
    }

    public void eliminar(Long id) {
        metodoPagoRepository.deleteById(id);
    }
}