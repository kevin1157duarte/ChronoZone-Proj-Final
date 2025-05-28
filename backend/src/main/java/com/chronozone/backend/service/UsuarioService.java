package com.chronozone.backend.service;

// Importações necessárias
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chronozone.backend.model.Usuario;
import com.chronozone.backend.repository.UsuarioRepository;


// A camada service é onde fica a lógica do sistema. Por exemplo, 
// se antes de salvar um post você quiser validar o conteúdo, essa lógica estaria aqui.

@Service // Indica que esta classe é um componente de serviço do Spring
public class UsuarioService {

    @Autowired // Injeta automaticamente o repositório de usuários
    private UsuarioRepository usuarioRepository;

    // Método que retorna todos os usuários cadastrados no banco de dados
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Método que salva um novo usuário ou atualiza um existente
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método que deleta um usuário pelo ID
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método que busca um usuário específico pelo ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null); // Se não encontrar, retorna null
    }
}
