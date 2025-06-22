package com.perfulandia.perfulandia.entities;

import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "usuarios")
public class Usuario {
    //ATRIBUTOS
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rut;
    private String nombre;
    private String email;
    private String contrasena;
    private String rol;


    //CONSTRUCTORES

    public Usuario() {
    }

    public Usuario(String rut, String nombre, String contrasena, String rol, String email) {
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;

    }

    //GETTERS AND SETTERS

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    
    //METODOS
}

 
