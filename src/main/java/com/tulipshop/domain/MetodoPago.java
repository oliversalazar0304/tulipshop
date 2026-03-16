package com.tulipshop.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "metodos_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_tarjeta", nullable = false, length = 50)
    private String tipoTarjeta;

    @Column(name = "numero_enmascarado", nullable = false, length = 20)
    private String numeroEnmascarado;

    @Column(name = "nombre_titular", nullable = false, length = 100)
    private String nombreTitular;

    @Column(name = "fecha_expiracion", nullable = false, length = 10)
    private String fechaExpiracion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public MetodoPago() {
    }

    public Long getId() {
        return id;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getNumeroEnmascarado() {
        return numeroEnmascarado;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public void setNumeroEnmascarado(String numeroEnmascarado) {
        this.numeroEnmascarado = numeroEnmascarado;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}