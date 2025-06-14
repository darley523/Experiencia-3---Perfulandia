package com.perfulandia.perfulandia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.perfulandia.entities.Boleta;
import com.perfulandia.perfulandia.services.BoletaService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/boletas")
public class BoletaController {

    @Autowired
    private BoletaService service;

    @GetMapping
    public List<Boleta> List(){
        return service.findByAll();
    }

    @GetMapping("/{numeroBoleta}")
    public ResponseEntity<?> verDetalle(@PathVariable Long numeroBoleta){
        Optional<Boleta> boletaOptional = service.findById(numeroBoleta);
        if (boletaOptional.isPresent()){
            return ResponseEntity.ok(boletaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Boleta boleta){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(boleta));
    }

    @PutMapping("/{numeroBoleta}")
    public ResponseEntity<?> modificar(@PathVariable Long numeroBoleta, @RequestBody Boleta boleta){
        Optional<Boleta> boletaOptional = service.findById(numeroBoleta);
        if(boletaOptional.isPresent()){
            Boleta boletaExistente = boletaOptional.get();
            boletaExistente.setRutComprador(boleta.getRutComprador());
            boletaExistente.setCantidadProductos(boleta.getCantidadProductos());
            boletaExistente.setPrecio(boleta.getPrecio());
            boletaExistente.setDescripcion(boleta.getDescripcion());
            boletaExistente.setFecha(boleta.getFecha());

            Boleta boletaModificado = service.save(boletaExistente);
            return ResponseEntity.ok(boletaModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numeroBoleta}")
    public ResponseEntity<?> eliminar(@PathVariable Long numeroBoleta){
        Boleta boleta = new Boleta();
        boleta.setNumeroBoleta(numeroBoleta);
        Optional<Boleta> boletaOptional = service.delete(boleta);
        if(boletaOptional.isPresent()){
            return ResponseEntity.ok(boletaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
