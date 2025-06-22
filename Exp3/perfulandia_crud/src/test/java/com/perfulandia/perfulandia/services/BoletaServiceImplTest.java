package com.perfulandia.perfulandia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Boleta;
import com.perfulandia.perfulandia.repository.BoletaRepository;
//import com.perfulandia.perfulandia.services.BoletaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BoletaServiceImplTest {

    @InjectMocks
    private BoletaServiceImpl service;

    @Mock
    private BoletaRepository repository;

    List<Boleta> boletaList = new ArrayList<>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        cargarBoletas();
    }

    @Test
    public void buscarTodosTest() {
        when(repository.findAll()).thenReturn(boletaList);

        List<Boleta> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void buscarPorIdTest() {
        Boleta boleta = new Boleta(2L, "12.345.678-9", 5, 49990, "2025-06-17", "Essenza Noire, Velvet Storm, Mystic Amber");
        when(repository.findById(2L)).thenReturn(Optional.of(boleta));

        Optional<Boleta> response = service.findById(2L);

        assertTrue(response.isPresent());
        assertEquals("12.345.678-9", response.get().getRutComprador());
        assertEquals("Essenza Noire, Velvet Storm, Mystic Amber", response.get().getDescripcion());
        verify(repository, times(1)).findById(2L);
    }

    @Test
    public void crearBoletaTest() {
        Boleta boletaSinId = new Boleta(null, "11.222.333-4", 2, 19990, "2025-06-10", "Luna Ardiente, Mystic Amber");
        Boleta boletaConId = new Boleta(10L, "11.222.333-4", 2, 19990, "2025-06-10", "Luna Ardiente, Mystic Amber");

        when(repository.save(boletaSinId)).thenReturn(boletaConId);

        Boleta response = service.save(boletaSinId);

        assertNotNull(response);
        assertEquals(10L, response.getNumeroBoleta());
        assertEquals("Luna Ardiente, Mystic Amber", response.getDescripcion());
        verify(repository, times(1)).save(boletaSinId);
    }

    @Test
    public void modificarBoletaTest() {
        Boleta boletaOriginal = new Boleta(3L, "11.222.333-4", 2, 19990, "2025-06-10", "Luna Ardiente, Mystic Amber");
        Boleta boletaModificada = new Boleta(3L, "11.222.333-4", 4, 39980, "2025-06-10", "Essenza Noire, Luna Ardiente, Mystic Amber Intense");

        when(repository.findById(3L)).thenReturn(Optional.of(boletaOriginal));
        when(repository.save(any(Boleta.class))).thenReturn(boletaModificada);

        Optional<Boleta> boletaBD = service.findById(3L);
        Boleta updatedBoleta = null;
        if (boletaBD.isPresent()) {
            Boleta boleta = boletaBD.get();
            boleta.setCantidadProductos(4);
            boleta.setPrecio(39980);
            boleta.setDescripcion("Essenza Noire, Luna Ardiente, Mystic Amber Intense");

            updatedBoleta = service.save(boleta);
        }

        assertNotNull(updatedBoleta);
        assertEquals(4, updatedBoleta.getCantidadProductos());
        assertEquals(39980, updatedBoleta.getPrecio());
        assertEquals("Essenza Noire, Luna Ardiente, Mystic Amber Intense", updatedBoleta.getDescripcion());

        verify(repository).findById(3L);
        verify(repository).save(any(Boleta.class));
    }

    @Test
    public void eliminarBoletaTest() {
        Boleta boleta = new Boleta(4L, "10.111.222-3", 3, 29990, "2025-06-01", "Essenza Noire, Velvet Storm");

        when(repository.findById(4L)).thenReturn(Optional.of(boleta));

        Optional<Boleta> response = service.delete(boleta);

        assertTrue(response.isPresent());
        assertEquals("Essenza Noire, Velvet Storm", response.get().getDescripcion());

        verify(repository, times(1)).findById(4L);
        verify(repository, times(1)).delete(boleta);
    }

    private void cargarBoletas() {
        boletaList.add(new Boleta(1L, "10.111.222-3", 2, 29990, "2025-06-01", "Essenza Noire, Velvet Storm"));
        boletaList.add(new Boleta(2L, "12.345.678-9", 5, 49990, "2025-06-17", "Essenza Noire, Velvet Storm, Mystic Amber"));
        boletaList.add(new Boleta(3L, "11.222.333-4", 2, 19990, "2025-06-10", "Luna Ardiente, Mystic Amber"));
    }
}
