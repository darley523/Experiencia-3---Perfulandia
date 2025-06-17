package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Envio;

public interface EnvioService {
    
    List<Envio> findByAll();

    Optional<Envio> findById(Long numEnvio);

    Envio save(Envio envio);
    
    Optional<Envio> delete (Envio envio);

}