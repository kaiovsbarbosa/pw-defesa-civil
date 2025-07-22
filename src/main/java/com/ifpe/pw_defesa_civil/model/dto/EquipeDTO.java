package com.ifpe.pw_defesa_civil.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;

public class EquipeDTO {

    private Long id;
    private String nomeEquipe;
    private List<Long> membrosIds;

    public EquipeDTO() {
    }

    public EquipeDTO(Long id, String nomeEquipe, List<Long> membrosIds) {
        this.id = id;
        this.nomeEquipe = nomeEquipe;
        this.membrosIds = membrosIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public List<Long> getMembrosIds() {
        return membrosIds;
    }

    public void setMembrosIds(List<Long> membrosIds) {
        this.membrosIds = membrosIds;
    }

    public static EquipeDTO fromEntity(Equipe equipe) {
        EquipeDTO dto = new EquipeDTO();
        dto.setId(equipe.getId());
        dto.setNomeEquipe(equipe.getNomeEquipe());

        if (equipe.getMembros() != null) {
            dto.setMembrosIds(equipe.getMembros().stream()
                    .map(Usuario::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Equipe toEntity() {
        Equipe equipe = new Equipe();
        equipe.setId(this.id);
        equipe.setNomeEquipe(this.nomeEquipe);

        return equipe;
    }
}