package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.perfulandia.entities.Producto;
import com.perfulandia.perfulandia.repository.ProductoRepository;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired //springboot definira la notacion que corresponda
    private ProductoRepository repository;


    @Override
    @Transactional(readOnly = true) //para cuidar integridad, no necesito que se tengan mas permisos sobre la tabla, Transactional para controlar posibles errores?
    public List<Producto> findByAll() {

        return (List<Producto>) repository.findAll();
    
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        return repository.findById(id);
    }


    @Override
    @Transactional
    public Optional<Producto> delete(Producto unProducto) {
        Optional<Producto> productoOptional = repository.findById(unProducto.getId());
        productoOptional.ifPresent(productoDb ->{
            repository.delete(unProducto);
        });
        return productoOptional;
    }


    @Override
    @Transactional //Transaccion a la bdd?
    public Producto save(Producto unProducto) {
        return repository.save(unProducto);
    }

    
}