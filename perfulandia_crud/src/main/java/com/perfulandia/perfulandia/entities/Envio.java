package com.perfulandia.perfulandia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "envios")
public class Envio {

    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_envio")
    private Long numEnvio;

    @Column(name = "rut_comprador")
    private String rutComprador;

    @Column(name = "numero_boleta")
    private Long numBoleta;

    private String estado;

    public Envio() {
    }

    public Envio(Long numEnvio, String rutComprador, Long numBoleta, String estado) {
        this.numEnvio = numEnvio;
        this.rutComprador = rutComprador;
        this.numBoleta = numBoleta;
        this.estado = estado;
    }

    public Long getNumEnvio() {
        return numEnvio;
    }

    public void setNumEnvio(Long numEnvio) {
        this.numEnvio = numEnvio;
    }

    public String getRutComprador() {
        return rutComprador;
    }

    public void setRutComprador(String rutComprador) {
        this.rutComprador = rutComprador;
    }

    public Long getNumBoleta() {
        return numBoleta;
    }

    public void setNumBoleta(Long numBoleta) {
        this.numBoleta = numBoleta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}