package com.chronozone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronozone.backend.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

}
