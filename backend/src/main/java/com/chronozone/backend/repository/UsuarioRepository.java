package com.chronozone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronozone.backend.model.Usuario;

// A camada repository faz a ponte com o banco de dados. 
// Ela é responsável por salvar, buscar, deletar ou atualizar informações no banco de dados.

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

}
