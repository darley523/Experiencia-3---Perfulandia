package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.perfulandia.entities.Boleta;
import com.perfulandia.perfulandia.repository.BoletaRepository;

@Service
public class BoletaServiceImpl implements BoletaService{
    
    @Autowired
    private BoletaRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Boleta> findByAll() {
        
        return (List<Boleta>) repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boleta> findById(Long boleta) {

        return repo.findById(boleta);
    }

    @Override
    @Transactional
    public Optional<Boleta> delete(Boleta boleta) {
        Optional<Boleta> boletaOptional = repo.findById(boleta.getNumeroBoleta());
        boletaOptional.ifPresent(boletaDb ->{
            repo.delete(boleta);
        });
        return boletaOptional;
    }

    @Override
    @Transactional
    public Boleta save(Boleta boleta) {

        return repo.save(boleta);
    }


}
