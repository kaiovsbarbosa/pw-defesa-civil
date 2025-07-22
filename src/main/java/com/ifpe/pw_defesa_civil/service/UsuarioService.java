package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpe.pw_defesa_civil.model.dto.AtualizarUsuarioDTO;
import com.ifpe.pw_defesa_civil.model.dto.SalvarUsuarioDTO;
import com.ifpe.pw_defesa_civil.model.dto.UsuarioDTO;
import com.ifpe.pw_defesa_civil.model.entity.Perfil;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.PerfilRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.perfilRepository = perfilRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario save(SalvarUsuarioDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        
        Perfil perfil = perfilRepository.findById(usuarioCreateDTO.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        usuario.setPerfil(perfil);
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, AtualizarUsuarioDTO usuarioUpdateDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        usuario.setNome(usuarioUpdateDTO.getNome());
        usuario.setEmail(usuarioUpdateDTO.getEmail());
        
        if (usuarioUpdateDTO.getSenha() != null && !usuarioUpdateDTO.getSenha().isEmpty()) {
            usuario.setSenhaHash(passwordEncoder.encode(usuarioUpdateDTO.getSenha()));
        }
        
        if (usuarioUpdateDTO.getPerfilId() != null) {
            Perfil perfil = perfilRepository.findById(usuarioUpdateDTO.getPerfilId())
                    .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
            usuario.setPerfil(perfil);
        }
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO toDto(Usuario usuario) {
        return UsuarioDTO.fromEntity(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }
}
