package com.perfulandia.perfulandia.controllers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandia.entities.Producto;
import com.perfulandia.perfulandia.services.ProductServiceImpl;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductServiceImpl productoserviceimpl;

    private List<Producto> productosLista;

    //VER TODOS LOS PRODUCTOS
    @Test
    public void verProductosTest() throws Exception{
        when(productoserviceimpl.findByAll()).thenReturn(productosLista);
        mockmvc.perform(get("/api/productos")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    //ENCONTRAR UN SOLO PRODUCTO
    @Test
    public void verunProductoTest(){
        Producto unProducto = new Producto(1L,"Channel Lestro", "Perfume de mujer 300ML",50000,100) ;
        try{
            when (productoserviceimpl.findById(1L)).thenReturn(Optional.of(unProducto));
            mockmvc.perform(get("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail ("El testing arrojó un error "+ ex.getMessage());
        }
    }

    //Buscar producto inexsistente
    @Test
    public void productoNoExisteTest() throws Exception{
        when(productoserviceimpl.findById(20L)).thenReturn(Optional.empty());
        mockmvc.perform((get("/api/productos/20"))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    //CREAR PRODUCTO
    @Test
    public void crearProductoTest() throws Exception{
        Producto unProducto = new Producto(null, "Anto", "Perfume mixto 100ml", 100, 10);
        Producto otroProducto = new Producto(12L, "Antonio Teteras Premium", "Perfume hombre 75ML",50000,5);
        when (productoserviceimpl.save(any(Producto.class))).thenReturn(otroProducto);
        mockmvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(unProducto)))
            .andExpect(status().isCreated());
    }    

    // MODIFICAR PRODUCTO EXISTENTE
    @Test
    public void modificarProductoTest() throws Exception {
        Producto original = new Producto(5L, "Old Perfume", "Descripción vieja", 30000, 15);
        Producto modificado = new Producto(5L, "New Perfume", "Descripción nueva", 35000, 20);

        when(productoserviceimpl.findById(5L)).thenReturn(Optional.of(original));
        when(productoserviceimpl.save(any(Producto.class))).thenReturn(modificado);

        mockmvc.perform(put("/api/productos/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(modificado)));
    }

    // INTENTAR MODIFICAR PRODUCTO INEXISTENTE
    @Test
    public void modificarProductoNoExisteTest() throws Exception {
        Producto modificado = new Producto(99L, "Nombre", "Desc", 10000, 1);

        when(productoserviceimpl.findById(99L)).thenReturn(Optional.empty());

        mockmvc.perform(put("/api/productos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isNotFound());
    }

    // ELIMINAR PRODUCTO EXISTENTE
    @Test
    public void eliminarProductoTest() throws Exception {
        Producto productoEliminar = new Producto(7L, "Eliminar", "Desc", 12345, 2);

        when(productoserviceimpl.delete(any(Producto.class))).thenReturn(Optional.of(productoEliminar));

        mockmvc.perform(delete("/api/productos/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productoEliminar)));
    }

    // INTENTAR ELIMINAR PRODUCTO INEXISTENTE
    @Test
    public void eliminarProductoNoExisteTest() throws Exception {
        when(productoserviceimpl.delete(any(Producto.class))).thenReturn(Optional.empty());

        mockmvc.perform(delete("/api/productos/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
