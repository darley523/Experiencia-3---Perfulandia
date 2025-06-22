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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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

    // Modificar boleta existente
    @Test
    public void modificarBoletaTest() throws Exception {
        Boleta original = new Boleta(5L, "12.345.678-9", 3, 30000, "2024-06-17", "Compra original");
        Boleta modificada = new Boleta(5L, "12.345.678-9", 5, 50000, "2024-06-18", "Compra modificada");

        when(boletaServiceImpl.findById(5L)).thenReturn(Optional.of(original));
        when(boletaServiceImpl.save(any(Boleta.class))).thenReturn(modificada);

        mockmvc.perform(put("/api/boletas/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificada)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(modificada)));
    }

    // Intentar modificar boleta inexistente
    @Test
    public void modificarBoletaNoExisteTest() throws Exception {
        Boleta modificada = new Boleta(99L, "9.999.999-9", 1, 10000, "2024-06-17", "Mod inexistente");

        when(boletaServiceImpl.findById(99L)).thenReturn(Optional.empty());

        mockmvc.perform(put("/api/boletas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificada)))
                .andExpect(status().isNotFound());
    }

    // Eliminar boleta existente
    @Test
    public void eliminarBoletaTest() throws Exception {
        Boleta boletaEliminar = new Boleta(7L, "22.222.222-2", 1, 10000, "2024-06-17", "Eliminar prueba");

        when(boletaServiceImpl.delete(any(Boleta.class))).thenReturn(Optional.of(boletaEliminar));

        mockmvc.perform(delete("/api/boletas/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(boletaEliminar)));
    }


}
