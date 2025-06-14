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

import com.perfulandia.perfulandia.entities.Producto;
import com.perfulandia.perfulandia.services.ProductService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/productos")
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping
    public List<Producto> List(){
        return service.findByAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()){
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    } 

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Producto unProducto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unProducto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Producto unProducto){
        Optional<Producto> productoOptional = service.findById(id);
        if(productoOptional.isPresent()){
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(unProducto.getNombre());
            productoExistente.setDescripcion(unProducto.getDescripcion());
            productoExistente.setPrecio(unProducto.getPrecio());
            productoExistente.setCantidad(unProducto.getCantidad());
            Producto productoModificado = service.save(productoExistente);
            return ResponseEntity.ok(productoModificado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Producto unProducto = new Producto();
        unProducto.setId(id);
        Optional<Producto> productoOptional = service.delete(unProducto);
        if(productoOptional.isPresent()){
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }



}
