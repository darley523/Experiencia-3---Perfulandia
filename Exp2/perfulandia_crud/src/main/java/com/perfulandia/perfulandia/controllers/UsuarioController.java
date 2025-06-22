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

import com.perfulandia.perfulandia.entities.Usuario;
import com.perfulandia.perfulandia.services.UsuarioService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> List(){
        return service.findByAll();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> verDetalle(@PathVariable String rut){
        Optional<Usuario> usuarioOptional = service.findById(rut);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Usuario user){{
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }}

    @PutMapping("/{rut}")
    public ResponseEntity<?> modificar (@PathVariable String rut, @RequestBody Usuario user){
        Optional<Usuario> usuarioOptional = service.findById(rut);
        if(usuarioOptional.isPresent()){
            Usuario userExistente = usuarioOptional.get();
            userExistente.setNombre(user.getNombre());
            userExistente.setContrasena(user.getContrasena());
            userExistente.setRol(user.getRol());
            userExistente.setEmail(user.getEmail());
            Usuario userModificado = service.save(userExistente);
            return ResponseEntity.ok(userModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminar (@PathVariable String rut){
        Usuario user = new Usuario();
        user.setRut(rut);
        Optional<Usuario> userOptional = service.delete(user);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
