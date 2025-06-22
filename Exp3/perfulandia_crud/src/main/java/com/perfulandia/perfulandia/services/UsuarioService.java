package com.perfulandia.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.perfulandia.perfulandia.entities.Usuario;

public interface UsuarioService {

    List<Usuario> findByAll();

    Optional<Usuario> findById(String rut);

    Usuario save(Usuario user);

    Optional<Usuario> delete(Usuario user);

}
