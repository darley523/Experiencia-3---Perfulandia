package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Producto;

public interface ProductService {

    List<Producto> findByAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto unProducto);
    
    Optional<Producto> delete (Producto unProducto);

}
