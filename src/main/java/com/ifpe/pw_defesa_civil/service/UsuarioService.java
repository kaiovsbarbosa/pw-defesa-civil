package com.ifpe.pw_defesa_civil.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.EquipeRepository;
import com.ifpe.pw_defesa_civil.repository.PerfilRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PerfilRepository perfilRepository,
        EquipeRepository equipeRepository
    ) 
    {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional()
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
        // return usuarioRepository.findAll().stream()
        //         .map(UsuarioDTO::new)
        //         .toList();
    }
}
