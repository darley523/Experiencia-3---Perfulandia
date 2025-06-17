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
import com.perfulandia.perfulandia.entities.Usuario;
import com.perfulandia.perfulandia.services.UsuarioServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioServiceImpl usuarioServiceImpl;

    private List<Usuario> usuariosLista;

    // Ver todos los usuarios
    @Test
    public void verUsuariosTest() throws Exception {
        when(usuarioServiceImpl.findByAll()).thenReturn(usuariosLista);
        mockmvc.perform(get("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Ver un usuario
    @Test
    public void verUnUsuarioTest() {
        Usuario user = new Usuario("10.111.222-3", "Juan", "correo@gmail.com", "clave123", "Admin");
        try {
            when(usuarioServiceImpl.findById("10.111.222-3")).thenReturn(Optional.of(user));
            mockmvc.perform(get("/api/usuarios/10.111.222-3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing arrojó un error " + ex.getMessage());
        }
    }

    // Usuario no existe
    @Test
    public void usuarioNoExisteTest() throws Exception {
        when(usuarioServiceImpl.findById("99.999.999-9")).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/usuarios/99.999.999-9")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Crear usuario
    @Test
    public void crearUsuarioTest() throws Exception {
        Usuario nuevoUsuario = new Usuario(null, "Ana", "ana@email.com", "clave123", "Cliente");
        Usuario usuarioGuardado = new Usuario("12.345.678-9", "Ana", "ana@email.com", "clave123", "Cliente");
        when(usuarioServiceImpl.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        mockmvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated());
    }
}
