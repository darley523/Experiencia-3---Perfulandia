package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.perfulandia.entities.Envio;
import com.perfulandia.perfulandia.repository.EnvioRepository;

@Service
public class EnvioServiceImpl implements EnvioService {

    @Autowired
    private EnvioRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Envio> findByAll() {
        return (List<Envio>) repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Envio> findById(Long numEnvio) {
        return repo.findById(numEnvio);
    }

    @Override
    @Transactional
    public Optional<Envio> delete(Envio envio) {
        Optional<Envio> envioOptional = repo.findById(envio.getNumEnvio());
        envioOptional.ifPresent(envioDb -> {
            repo.delete(envio);
        });
        return envioOptional;
    }

    @Override
    @Transactional
    public Envio save(Envio envio) {
        return repo.save(envio);
    }

}
