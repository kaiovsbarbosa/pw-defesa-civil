package com.ifpe.pw_defesa_civil.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.model.entity.Perfil;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;

public record UsuarioDTO(
    Long id,
    String nome,
    String email,
    Perfil perfil,
    List<Long> equipesIds
) {
    public UsuarioDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil(),
            usuario.getEquipes().stream().map(Equipe::getId).collect(Collectors.toList())
        );
    }
}