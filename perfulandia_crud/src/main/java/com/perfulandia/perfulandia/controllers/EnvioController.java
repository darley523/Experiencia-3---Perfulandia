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

import com.perfulandia.perfulandia.entities.Envio;
import com.perfulandia.perfulandia.services.EnvioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/envios")
public class EnvioController {

    @Autowired
    private EnvioService service;

    @GetMapping
    public List<Envio> list(){
        return service.findByAll();
    }

    @GetMapping("/{numEnvio}")
    public ResponseEntity<?> verDetalle(@PathVariable Long numEnvio){
        Optional<Envio> envioOptional = service.findById(numEnvio);
        if (envioOptional.isPresent()){
            return ResponseEntity.ok(envioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Envio envio){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(envio));
    }

    @PutMapping("/{numEnvio}")
    public ResponseEntity<?> modificar(@PathVariable Long numEnvio, @RequestBody Envio envio){
        Optional<Envio> envioOptional = service.findById(numEnvio);
        if(envioOptional.isPresent()){
            Envio envioExistente = envioOptional.get();
            
            envioExistente.setRutComprador(envio.getRutComprador());
            envioExistente.setNumBoleta(envio.getNumBoleta());
            envioExistente.setNumPedido(envio.getNumPedido());
            envioExistente.setEstado(envio.getEstado());

            Envio envioModificado = service.save(envioExistente);
            return ResponseEntity.ok(envioModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numEnvio}")
    public ResponseEntity<?> eliminar(@PathVariable Long numEnvio){
        Envio envio = new Envio();
        envio.setNumEnvio(numEnvio);
        Optional<Envio> envioOptional = service.delete(envio);
        if(envioOptional.isPresent()){
            return ResponseEntity.ok(envioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
