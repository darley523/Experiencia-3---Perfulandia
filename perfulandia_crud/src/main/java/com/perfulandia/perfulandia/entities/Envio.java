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
    @Column(name = "num_envio")
    private Long numEnvio;

    @Column(name = "rut_comprador")
    private String rutComprador;

    @Column(name = "num_boleta")
    private Long numBoleta;

    @Column(name = "num_pedido")
    private Long numPedido;

    private String estado;

    public Envio() {
    }

    public Envio(Long numEnvio, String rutComprador, Long numBoleta, Long numPedido, String estado) {
        this.numEnvio = numEnvio;
        this.rutComprador = rutComprador;
        this.numBoleta = numBoleta;
        this.numPedido = numPedido;
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

    public Long getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Long numPedido) {
        this.numPedido = numPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}