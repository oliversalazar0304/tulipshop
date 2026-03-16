package com.tulipshop.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 120)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 250)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    public Usuario() {
    }

    public Usuario(Long id, String nombreCompleto, String correo, String password, String direccion, Rol rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.password = password;
        this.direccion = direccion;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public String getDireccion() {
        return direccion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}