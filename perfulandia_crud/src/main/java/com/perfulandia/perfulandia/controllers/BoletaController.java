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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Tag(name = "Boletas", description = "Operaciones relacionadas con boletas")
@RestController
@RequestMapping("api/boletas")
public class BoletaController {

    @Autowired
    private BoletaService service;

    //Anotaciones para la documentacion del metodo findByAll
    @Operation(summary = "Obtener lista de boletas", description = "Devuelve todas las boletas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de boletas retornada correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Boleta.class)))
    @GetMapping
    public List<Boleta> List(){
        return service.findByAll();
    }


    @Operation(summary = "Obtener boleta por número", description = "Obtiene el detalle de una boleta específica por su número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boleta.class))),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    @GetMapping("/{numeroBoleta}")
    public ResponseEntity<?> verDetalle(@PathVariable Long numeroBoleta){
        Optional<Boleta> boletaOptional = service.findById(numeroBoleta);
        if (boletaOptional.isPresent()){
            return ResponseEntity.ok(boletaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    //Anotaciones para la documentacion del metodo crear
    @Operation(summary = "Crear una nueva boleta", description = "Crea una boleta con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Boleta creada correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boleta.class)))
    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Boleta boleta){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(boleta));
    }

    //Anotaciones para la documentacion del metodo modificar
    @Operation(summary = "Modificar boleta existente", description = "Actualiza los datos de una boleta identificada por su número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta modificada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boleta.class))),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
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

    //Anotaciones para la documentacion del metodo eliminar
    @Operation(summary = "Eliminar boleta por número", description = "Elimina una boleta existente identificada por su número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta eliminada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boleta.class))),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
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
