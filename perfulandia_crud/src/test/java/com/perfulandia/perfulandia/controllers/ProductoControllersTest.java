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


@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllersTest {
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

}
