package com.chronozone.backend.controller;

// Importações necessárias
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chronozone.backend.model.Usuario;
import com.chronozone.backend.repository.UsuarioRepository;
import com.chronozone.backend.service.UsuarioService;

import lombok.Data;

// controller é responsável por receber as requisições feitas pelo usuário 
// (por exemplo, ao acessar uma página ou clicar em um botão) e responder com as informações necessárias.

@RestController // Indica que esta classe é um controller REST (API)
@RequestMapping("/usuarios") // Define o caminho base da API para essa classe
public class UsuarioController {

    // Classe interna que representa os dados recebidos no login
    @Data
    public class LoginRequest {
        private String email;
        private String senha;

        // Getters e setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }

    @Autowired // Injeta automaticamente a dependência do service
    private UsuarioService usuarioService;

    @Autowired // Injeta automaticamente a dependência do repository
    private UsuarioRepository usuarioRepository;

    // Método GET para listar todos os usuários
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // Método POST para criar um novo usuário
    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    // Método POST para realizar login (autenticação)
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        Usuario usuario = usuarioRepository.findByEmail(email);

        // Verifica se o usuário existe e se a senha está correta
        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuario.setSenha(null); // Nunca retornar a senha para o frontend
            return ResponseEntity.ok(usuario);
        }

        // Se não for autenticado, retorna status 401 (não autorizado)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Método PUT para atualizar um usuário existente pelo ID
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existente = usuarioService.buscarPorId(id);
        if (existente != null) {
            existente.setNome(usuario.getNome());
            existente.setEmail(usuario.getEmail());
            return usuarioService.salvar(existente);
        } else {
            return null;
        }
    }

    // Método DELETE para deletar um usuário pelo ID
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }

    // Método GET para buscar um usuário específico pelo ID
    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }
}
