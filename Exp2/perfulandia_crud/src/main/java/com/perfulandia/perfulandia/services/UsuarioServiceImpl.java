package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.perfulandia.entities.Usuario;
import com.perfulandia.perfulandia.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repo;

    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByAll() {
        
        return (List<Usuario>) repo.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(String rut) {

        return repo.findById(rut);
    }

    @Override
    @Transactional
    public Optional<Usuario> delete(Usuario user) {
        Optional<Usuario> usuarioOptional = repo.findById(user.getRut());
        usuarioOptional.ifPresent(usuarioDb ->{
            repo.delete(user);
        });
        return usuarioOptional;
    }


    @Override
    @Transactional
    public Usuario save(Usuario user) {

        return repo.save(user);
    }
    
}
