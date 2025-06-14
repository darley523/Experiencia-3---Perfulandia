package com.perfulandia.perfulandia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "boletas")
public class Boleta {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_boleta")
    private long numeroBoleta;

    @Column(name = "rut_comprador")
    private String rutComprador;

    @Column(name = "cantidad_productos")
    private int cantidadProductos;
    
    private int precio;
    private String fecha;
    private String descripcion;

    //CONSTRUCTORES
    public Boleta(){

    }

    public Boleta(long numeroBoleta, String rutComprador, int cantidadProductos, int precio, String fecha,
            String descripcion) {
        this.numeroBoleta = numeroBoleta;
        this.rutComprador = rutComprador;
        this.cantidadProductos = cantidadProductos;
        this.precio = precio;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    
    //GETTERS AND SETTERS

    public long getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(long numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }

    public String getRutComprador() {
        return rutComprador;
    }

    public void setRutComprador(String rutComprador) {
        this.rutComprador = rutComprador;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    

}
