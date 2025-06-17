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
import com.perfulandia.perfulandia.entities.Boleta;
import com.perfulandia.perfulandia.services.BoletaServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoletaControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BoletaServiceImpl boletaServiceImpl;

    private List<Boleta> listaBoletas;

    // Ver todas las boletas
    @Test
    public void verBoletasTest() throws Exception {
        when(boletaServiceImpl.findByAll()).thenReturn(listaBoletas);
        mockmvc.perform(get("/api/boletas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Ver una boleta
    @Test
    public void verUnaBoletaTest() {
        Boleta b = new Boleta(1L, "10.222.333-4", 2, 15000, "2024-06-17", "Compra perfumes");
        try {
            when(boletaServiceImpl.findById(1L)).thenReturn(Optional.of(b));
            mockmvc.perform(get("/api/boletas/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El test arrojó un error: " + ex.getMessage());
        }
    }

    // Boleta no existe
    @Test
    public void boletaNoExisteTest() throws Exception {
        when(boletaServiceImpl.findById(999L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/boletas/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Crear boleta
    @Test
    public void crearBoletaTest() throws Exception {
        Boleta nueva = new Boleta(0L, "11.111.111-1", 1, 20000, "2024-06-17", "Fragancia");
        Boleta guardada = new Boleta(10L, "11.111.111-1", 1, 20000, "2024-06-17", "Fragancia");
        when(boletaServiceImpl.save(any(Boleta.class))).thenReturn(guardada);

        mockmvc.perform(post("/api/boletas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated());
    }
}
