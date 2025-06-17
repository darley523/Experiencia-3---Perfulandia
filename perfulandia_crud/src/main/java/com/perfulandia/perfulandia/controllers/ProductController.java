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

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
@RestController
@RequestMapping("api/productos")
public class ProductController {

    
    //Para los test
    @Autowired
    private ProductService service;

    //Anotaciones para la documentacion del metodo findByAll
    @Operation(summary = "Obtener lista de productos", description = "Devuelve todos los productos disponibles")
    @ApiResponse(responseCode = "200",description = "Lista de productos retornada correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Producto.class)))
    @GetMapping
    public List<Producto> List(){
        return service.findByAll();
    }

    //Anotaciones para la documentacion del metodo findById
    @Operation(summary = "Obtener producto por ID", description = "Obtiene el detalle de un producto específico")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Producto encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()){
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    } 

    //Anotaciones para la documentacion del metodo crear
    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)))
    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Producto unProducto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unProducto));
    }

    //Anotaciones para la documentacion del metodo modificar
    @Operation(summary = "Modificar un producto existente", description = "Actualiza los datos de un producto específico según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Producto modificado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
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

    //Anotaciones para la documentacion del metodo eliminar
    @Operation(summary = "Eliminar producto por ID", description = "Elimina un producto existente identificado por su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
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
