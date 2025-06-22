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
import com.perfulandia.perfulandia.entities.Envio;
import com.perfulandia.perfulandia.services.EnvioServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EnvioServiceImpl envioServiceImpl;

    private List<Envio> listaEnvios;

    // Ver todos los envios
    @Test
    public void verEnviosTest() throws Exception {
        when(envioServiceImpl.findByAll()).thenReturn(listaEnvios);
        mockmvc.perform(get("/api/envios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Ver un envio
    @Test
    public void verUnEnvioTest() {
        Envio e = new Envio(1L, "10.222.333-4", 5L, "Enviado");
        try {
            when(envioServiceImpl.findById(1L)).thenReturn(Optional.of(e));
            mockmvc.perform(get("/api/envios/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El test arrojó un error: " + ex.getMessage());
        }
    }

    // Envio no existe
    @Test
    public void envioNoExisteTest() throws Exception {
        when(envioServiceImpl.findById(999L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/envios/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Crear envio
    @Test
    public void crearEnvioTest() throws Exception {
        Envio nuevo = new Envio(null, "11.111.111-1", 10L, "Pendiente");
        Envio guardado = new Envio(10L, "11.111.111-1", 10L, "Pendiente");
        when(envioServiceImpl.save(any(Envio.class))).thenReturn(guardado);

        mockmvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated());
    }

    // Modificar envio existente
    @Test
    public void modificarEnvioTest() throws Exception {
        Envio original = new Envio(5L, "12.345.678-9", 3L, "Pendiente");
        Envio modificado = new Envio(5L, "12.345.678-9", 3L, "Entregado");

        when(envioServiceImpl.findById(5L)).thenReturn(Optional.of(original));
        when(envioServiceImpl.save(any(Envio.class))).thenReturn(modificado);

        mockmvc.perform(put("/api/envios/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(modificado)));
    }

    // Intentar modificar envio inexistente
    @Test
    public void modificarEnvioNoExisteTest() throws Exception {
        Envio modificado = new Envio(99L, "9.999.999-9", 1L, "Cancelado");

        when(envioServiceImpl.findById(99L)).thenReturn(Optional.empty());

        mockmvc.perform(put("/api/envios/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isNotFound());
    }

    // Eliminar envio existente
    @Test
    public void eliminarEnvioTest() throws Exception {
        Envio envioEliminar = new Envio(7L, "22.222.222-2", 1L, "Pendiente");

        when(envioServiceImpl.delete(any(Envio.class))).thenReturn(Optional.of(envioEliminar));

        mockmvc.perform(delete("/api/envios/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(envioEliminar)));
    }

    // Intentar eliminar envio inexistente
    @Test
    public void eliminarEnvioNoExisteTest() throws Exception {
        when(envioServiceImpl.delete(any(Envio.class))).thenReturn(Optional.empty());

        mockmvc.perform(delete("/api/envios/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
