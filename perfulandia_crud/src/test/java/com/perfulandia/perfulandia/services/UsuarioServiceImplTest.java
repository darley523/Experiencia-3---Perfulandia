package com.perfulandia.perfulandia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Usuario;
import com.perfulandia.perfulandia.repository.UsuarioRepository;
//import com.perfulandia.perfulandia.services.UsuarioServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl service;

    @Mock
    private UsuarioRepository repository;

    List<Usuario> lista = new ArrayList<>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        cargarUsuarios();
    }

    @Test
    public void buscarTodosTest() {
        when(repository.findAll()).thenReturn(lista);

        List<Usuario> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void buscarPorIdTest() {
        Usuario user = new Usuario("14.567.789-2", "Valentina Rojas", "clave123", "User", "valentina.r@gmail.com");
        when(repository.findById("14.567.789-2")).thenReturn(Optional.of(user));

        Optional<Usuario> response = service.findById("14.567.789-2");

        assertTrue(response.isPresent());
        assertEquals("Valentina Rojas", response.get().getNombre());
        verify(repository, times(1)).findById("14.567.789-2");
    }

    @Test
    public void crearUsuarioTest() {
        Usuario sinRut = new Usuario("12.345.678-9", "Tomás Paredes", "bluepass", "Admin", "tomas.p@gmail.com");

        when(repository.save(sinRut)).thenReturn(sinRut);

        Usuario response = service.save(sinRut);

        assertNotNull(response);
        assertEquals("Tomás Paredes", response.getNombre());
        assertEquals("Admin", response.getRol());
        verify(repository, times(1)).save(sinRut);
    }

    @Test
    public void modificarUsuarioTest() {
        Usuario original = new Usuario("19.876.543-1", "Lucía Herrera", "pass123", "User", "lucia.h@gmail.com");
        Usuario modificado = new Usuario("19.876.543-1", "Lucía Herrera", "nuevaClave456", "Admin", "lucia.h@gmail.com");

        when(repository.findById("19.876.543-1")).thenReturn(Optional.of(original));
        when(repository.save(any(Usuario.class))).thenReturn(modificado);

        Optional<Usuario> usuarioBD = service.findById("19.876.543-1");
        Usuario updated = null;
        if (usuarioBD.isPresent()) {
            Usuario user = usuarioBD.get();
            user.setContrasena("nuevaClave456");
            user.setRol("Admin");

            updated = service.save(user);
        }

        assertNotNull(updated);
        assertEquals("nuevaClave456", updated.getContrasena());
        assertEquals("Admin", updated.getRol());
        verify(repository).findById("19.876.543-1");
        verify(repository).save(any(Usuario.class));
    }

    @Test
    public void eliminarUsuarioTest() {
        Usuario user = new Usuario("13.321.654-7", "Sofía Álvarez", "clave789", "User", "sofia.a@gmail.com");

        when(repository.findById("13.321.654-7")).thenReturn(Optional.of(user));

        Optional<Usuario> response = service.delete(user);

        assertTrue(response.isPresent());
        assertEquals("Sofía Álvarez", response.get().getNombre());
        verify(repository, times(1)).findById("13.321.654-7");
        verify(repository, times(1)).delete(user);
    }

    public void cargarUsuarios() {
        lista.add(new Usuario("10.123.456-1", "Gabriel Soto", "pass001", "User", "gabriel.soto@gmail.com"));
        lista.add(new Usuario("14.567.789-2", "Valentina Rojas", "clave123", "User", "valentina.r@gmail.com"));
        lista.add(new Usuario("19.876.543-1", "Lucía Herrera", "pass123", "User", "lucia.h@gmail.com"));
    }
}
