package com.perfulandia.perfulandia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Producto;
import com.perfulandia.perfulandia.repository.ProductoRepository;
//import com.perfulandia.perfulandia.services.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductoServiceImplTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductoRepository repository;

    List<Producto> list = new ArrayList<>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        cargarProductos();
    }

    @Test
    public void buscarTodosTest() {
        when(repository.findAll()).thenReturn(list);

        List<Producto> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void buscarPorIdTest() {
        Producto producto = new Producto(2L, "Velvet Storm", "perfume para hombre 100ML", 35990, 20);
        when(repository.findById(2L)).thenReturn(Optional.of(producto));

        Optional<Producto> response = service.findById(2L);

        assertTrue(response.isPresent());
        assertEquals("Velvet Storm", response.get().getNombre());
        verify(repository, times(1)).findById(2L);
    }

    @Test
    public void crearProductoTest() {
        Producto productoSinId = new Producto(null, "Luna Ardiente", "perfume para mujer 50ML", 28990, 15);
        Producto productoConId = new Producto(10L, "Luna Ardiente", "perfume para mujer 50ML", 28990, 15);

        when(repository.save(productoSinId)).thenReturn(productoConId);

        Producto response = service.save(productoSinId);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Luna Ardiente", response.getNombre());
        verify(repository, times(1)).save(productoSinId);
    }

    @Test
    public void modificarProductoTest() {
        Producto productoOriginal = new Producto(3L, "Mystic Amber", "perfume intenso para mujer", 31990, 10);
        Producto productoModificado = new Producto(3L, "Mystic Amber Intense", "fragancia renovada con notas cálidas", 34990, 12);

        when(repository.findById(3L)).thenReturn(Optional.of(productoOriginal));
        when(repository.save(any(Producto.class))).thenReturn(productoModificado);

        Optional<Producto> productoBD = service.findById(3L);
        Producto updatedProduct = null;
        if (productoBD.isPresent()) {
            Producto product = productoBD.get();
            product.setNombre("Mystic Amber Intense");
            product.setDescripcion("fragancia renovada con notas cálidas");
            product.setPrecio(34990);
            product.setCantidad(12);

            updatedProduct = service.save(product);
        }

        assertNotNull(updatedProduct);
        assertEquals("Mystic Amber Intense", updatedProduct.getNombre());
        assertEquals("fragancia renovada con notas cálidas", updatedProduct.getDescripcion());
        assertEquals(34990, updatedProduct.getPrecio());

        verify(repository).findById(3L);
        verify(repository).save(any(Producto.class));
    }

    @Test
    public void eliminarProductoTest() {
        Producto producto = new Producto(4L, "Oceanus Bleu", "perfume fresco para hombre 100ML", 44990, 10);

        when(repository.findById(4L)).thenReturn(Optional.of(producto));

        Optional<Producto> response = service.delete(producto);

        assertTrue(response.isPresent());
        assertEquals("Oceanus Bleu", response.get().getNombre());

        verify(repository, times(1)).findById(4L);
        verify(repository, times(1)).delete(producto);
    }

    public void cargarProductos() {
        list.add(new Producto(1L, "Essenza Noire", "perfume floral para mujer 100ML", 32990, 25));
        list.add(new Producto(2L, "Velvet Storm", "perfume para hombre 100ML", 35990, 20));
        list.add(new Producto(3L, "Mystic Amber", "perfume intenso para mujer", 31990, 10));
    }
}
