package com.ifpe.pw_defesa_civil.model.dto;

import com.ifpe.pw_defesa_civil.model.entity.Mapa;

public class MapaDTO {

    private Long id;
    private Long processoId;
    private String nomeArquivo;
    private String caminhoArquivo;
    private String descricao;

    public MapaDTO() {
    }

    public MapaDTO(Long id, Long processoId, String nomeArquivo, String caminhoArquivo, String descricao) {
        this.id = id;
        this.processoId = processoId;
        this.nomeArquivo = nomeArquivo;
        this.caminhoArquivo = caminhoArquivo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static MapaDTO fromEntity(Mapa mapa) {
        MapaDTO dto = new MapaDTO();
        dto.setId(mapa.getId());
        dto.setNomeArquivo(mapa.getNomeArquivo());
        dto.setCaminhoArquivo(mapa.getCaminhoArquivo());
        dto.setDescricao(mapa.getDescricao());

        if (mapa.getProcesso() != null) {
            dto.setProcessoId(mapa.getProcesso().getId());
        }

        return dto;
    }

    public Mapa toEntity() {
        Mapa mapa = new Mapa();
        mapa.setId(this.id);
        mapa.setNomeArquivo(this.nomeArquivo);
        mapa.setCaminhoArquivo(this.caminhoArquivo);
        mapa.setDescricao(this.descricao);

        return mapa;
    }
}