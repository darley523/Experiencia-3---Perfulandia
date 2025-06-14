package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Boleta;

public interface BoletaService {
    
    List<Boleta> findByAll();

    Optional<Boleta> findById(Long numeroBoleta);

    Boleta save(Boleta boleta);
    
    Optional<Boleta> delete (Boleta boleta);

}
