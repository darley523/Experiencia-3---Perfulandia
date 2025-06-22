package com.perfulandia.perfulandia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.perfulandia.entities.Usuario;
import com.perfulandia.perfulandia.services.UsuarioService;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    //Anotaciones para la documentacion del metodo findByAll
    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve todos los usuarios disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public List<Usuario> list() {
        return service.findByAll();
    }

    //Anotaciones para la documentacion del metodo findById
    @Operation(summary = "Obtener usuario por RUT", description = "Obtiene el detalle de un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{rut}")
    public ResponseEntity<?> verDetalle(@PathVariable String rut) {
        Optional<Usuario> usuarioOptional = service.findById(rut);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //Anotaciones para la documentacion del metodo crear
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    //Anotaciones para la documentacion del metodo modificar
    @Operation(summary = "Modificar un usuario existente", description = "Actualiza los datos de un usuario según su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{rut}")
    public ResponseEntity<?> modificar(@PathVariable String rut, @RequestBody Usuario user) {
        Optional<Usuario> usuarioOptional = service.findById(rut);
        if (usuarioOptional.isPresent()) {
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

    //Anotaciones para la documentacion del metodo eliminar
    @Operation(summary = "Eliminar un usuario por RUT", description = "Elimina un usuario según su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminar(@PathVariable String rut) {
        Usuario user = new Usuario();
        user.setRut(rut);
        Optional<Usuario> userOptional = service.delete(user);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
