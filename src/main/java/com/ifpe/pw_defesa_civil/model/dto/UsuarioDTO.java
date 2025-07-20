package com.ifpe.pw_defesa_civil.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private Long perfilId;
    private String perfilTipo;
    private List<Long> equipesIds;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nome, String email, Long perfilId, String perfilTipo, List<Long> equipesIds) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfilId = perfilId;
        this.perfilTipo = perfilTipo;
        this.equipesIds = equipesIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public String getPerfilTipo() {
        return perfilTipo;
    }

    public void setPerfilTipo(String perfilTipo) {
        this.perfilTipo = perfilTipo;
    }

    public List<Long> getEquipesIds() {
        return equipesIds;
    }

    public void setEquipesIds(List<Long> equipesIds) {
        this.equipesIds = equipesIds;
    }

    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        
        if (usuario.getPerfil() != null) {
            dto.setPerfilId(usuario.getPerfil().getId());
            dto.setPerfilTipo(usuario.getPerfil().getTipo().name());
        }
        
        if (usuario.getEquipes() != null) {
            dto.setEquipesIds(usuario.getEquipes().stream()
                .map(Equipe::getId)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        
        return usuario;
    }
}