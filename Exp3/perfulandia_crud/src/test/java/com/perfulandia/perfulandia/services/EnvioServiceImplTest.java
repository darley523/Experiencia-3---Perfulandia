package com.perfulandia.perfulandia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Envio;
import com.perfulandia.perfulandia.repository.EnvioRepository;
//import com.perfulandia.perfulandia.services.EnvioServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EnvioServiceImplTest {

    @InjectMocks
    private EnvioServiceImpl service;

    @Mock
    private EnvioRepository repository;

    private List<Envio> envioList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cargarEnvios();
    }

    @Test
    public void buscarTodosEnviosTest() {
        when(repository.findAll()).thenReturn(envioList);

        List<Envio> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void buscarEnvioPorIdTest() {
        Envio envioEjemplo = new Envio(2L, "12.345.678-9", 101L, "En camino");
        when(repository.findById(2L)).thenReturn(Optional.of(envioEjemplo));

        Optional<Envio> response = service.findById(2L);

        assertTrue(response.isPresent());
        assertEquals("12.345.678-9", response.get().getRutComprador());
        assertEquals("En camino", response.get().getEstado());
        verify(repository, times(1)).findById(2L);
    }

    @Test
    public void crearEnvioTest() {
        Envio envioSinId = new Envio(null, "98.765.432-1", 102L, "Pendiente");
        Envio envioConId = new Envio(10L, "98.765.432-1", 102L, "Pendiente");

        when(repository.save(envioSinId)).thenReturn(envioConId);

        Envio response = service.save(envioSinId);

        assertNotNull(response);
        assertEquals(10L, response.getNumEnvio());
        assertEquals("Pendiente", response.getEstado());
        verify(repository, times(1)).save(envioSinId);
    }

    @Test
    public void modificarEnvioTest() {
        Envio envioOriginal = new Envio(3L, "11.222.333-4", 103L, "Pendiente");
        Envio envioModificado = new Envio(3L, "11.222.333-4", 103L, "Entregado");

        when(repository.findById(3L)).thenReturn(Optional.of(envioOriginal));
        when(repository.save(any(Envio.class))).thenReturn(envioModificado);

        Optional<Envio> envioBD = service.findById(3L);
        Envio updatedEnvio = null;

        if (envioBD.isPresent()) {
            Envio envio = envioBD.get();
            envio.setEstado("Entregado");

            updatedEnvio = service.save(envio);
        }

        assertNotNull(updatedEnvio);
        assertEquals("Entregado", updatedEnvio.getEstado());

        verify(repository).findById(3L);
        verify(repository).save(any(Envio.class));
    }

    @Test
    public void eliminarEnvioTest() {
        Envio envio = new Envio(4L, "22.333.444-5", 104L, "Cancelado");

        when(repository.findById(4L)).thenReturn(Optional.of(envio));

        Optional<Envio> response = service.delete(envio);

        assertTrue(response.isPresent());
        assertEquals("Cancelado", response.get().getEstado());

        verify(repository, times(1)).findById(4L);
        verify(repository, times(1)).delete(envio);
    }

    private void cargarEnvios() {
        envioList.add(new Envio(1L, "10.111.222-3", 100L, "Pendiente"));
        envioList.add(new Envio(2L, "12.345.678-9", 101L, "En camino"));
        envioList.add(new Envio(3L, "11.222.333-4", 103L, "Pendiente"));
    }
}
