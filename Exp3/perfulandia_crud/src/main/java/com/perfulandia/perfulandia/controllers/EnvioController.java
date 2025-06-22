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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;


@Tag(name = "Envíos", description = "Operaciones relacionadas con Envíos")
@RestController
@RequestMapping("api/envios")
public class EnvioController {

    @Autowired
    private EnvioService service;


    // Anotaciones para la documentación del método findByAll
    @Operation(summary = "Listar todos los envíos", description = "Obtiene una lista con todos los envíos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de envíos",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class)))
    @GetMapping
    public List<Envio> list(){
        return service.findByAll();
    }

        
    // Anotaciones para la documentación del método findById
    @Operation(summary = "Obtener envío por ID", description = "Obtiene el detalle de un envío específico")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Envío encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
    @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/{numEnvio}")
    public ResponseEntity<?> verDetalle(@PathVariable Long numEnvio){
        Optional<Envio> envioOptional = service.findById(numEnvio);
        if (envioOptional.isPresent()){
            return ResponseEntity.ok(envioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // Anotaciones para la documentación del método crear
    @Operation(summary = "Crear un nuevo envío", description = "Crea un envío con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Envío creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class)))
    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Envio envio){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(envio));
    }


    // Anotaciones para la documentación del método modificar
    @Operation(summary = "Modificar un envío existente", description = "Actualiza los datos de un envío específico según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Envío modificado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
    @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @PutMapping("/{numEnvio}")
    public ResponseEntity<?> modificar(@PathVariable Long numEnvio, @RequestBody Envio envio){
        Optional<Envio> envioOptional = service.findById(numEnvio);
        if(envioOptional.isPresent()){
            Envio envioExistente = envioOptional.get();
            
            envioExistente.setRutComprador(envio.getRutComprador());
            envioExistente.setNumBoleta(envio.getNumBoleta());
            envioExistente.setEstado(envio.getEstado());

            Envio envioModificado = service.save(envioExistente);
            return ResponseEntity.ok(envioModificado);
        }
        return ResponseEntity.notFound().build();
    }

    // Anotaciones para la documentación del método eliminar
    @Operation(summary = "Eliminar envío por ID", description = "Elimina un envío existente identificado por su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Envío eliminado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
    @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
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
