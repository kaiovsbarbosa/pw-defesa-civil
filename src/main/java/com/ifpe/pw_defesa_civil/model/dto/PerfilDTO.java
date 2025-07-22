package com.ifpe.pw_defesa_civil.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ifpe.pw_defesa_civil.model.entity.Perfil;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.model.entity.Perfil.TipoPerfil;

public class PerfilDTO {

    private Long id;
    private String tipo;
    private List<Long> usuariosIds;

    public PerfilDTO() {
    }

    public PerfilDTO(Long id, String tipo, List<Long> usuariosIds) {
        this.id = id;
        this.tipo = tipo;
        this.usuariosIds = usuariosIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Long> getUsuariosIds() {
        return usuariosIds;
    }

    public void setUsuariosIds(List<Long> usuariosIds) {
        this.usuariosIds = usuariosIds;
    }

    public static PerfilDTO fromEntity(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());

        if (perfil.getTipo() != null) {
            dto.setTipo(perfil.getTipo().name());
        }

        if (perfil.getUsuarios() != null) {
            dto.setUsuariosIds(perfil.getUsuarios().stream()
                    .map(Usuario::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Perfil toEntity() {
        Perfil perfil = new Perfil();
        perfil.setId(this.id);

        if (this.tipo != null) {
            try {
                perfil.setTipo(TipoPerfil.valueOf(this.tipo));
            } catch (IllegalArgumentException e) {
                System.err.println("Tipo de perfil inválido no DTO: " + this.tipo);
                perfil.setTipo(null);
            }
        }

        return perfil;
    }
}