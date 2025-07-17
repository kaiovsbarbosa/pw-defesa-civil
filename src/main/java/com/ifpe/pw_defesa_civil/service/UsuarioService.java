package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.EquipeRepository;
import com.ifpe.pw_defesa_civil.repository.PerfilRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService{
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PerfilRepository perfilRepository,
        EquipeRepository equipeRepository
    ) 
    {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }
}
