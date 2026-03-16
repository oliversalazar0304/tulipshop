package com.tulipshop.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carritos")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, length = 20)
    private String estado;

    public Carrito() {
    }

    public Carrito(Usuario usuario, LocalDateTime fechaCreacion, String estado) {
        this.usuario = usuario;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
