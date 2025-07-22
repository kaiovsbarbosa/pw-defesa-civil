package com.ifpe.pw_defesa_civil.model.dto;

import com.ifpe.pw_defesa_civil.model.entity.Atualizacao;
import java.time.LocalDateTime;

public class AtualizacaoDTO {

    private Long id;
    private Long processoId;
    private Long usuarioId;
    private String usuarioNome;
    private LocalDateTime dataHora;
    private String descricao;

    public AtualizacaoDTO() {
    }

    public AtualizacaoDTO(Long id, Long processoId, Long usuarioId, String usuarioNome, LocalDateTime dataHora, String descricao) {
        this.id = id;
        this.processoId = processoId;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.dataHora = dataHora;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public static AtualizacaoDTO fromEntity(Atualizacao atualizacao) {
        AtualizacaoDTO dto = new AtualizacaoDTO();
        dto.setId(atualizacao.getId());
        dto.setDataHora(atualizacao.getDataHora());
        dto.setDescricao(atualizacao.getDescricao());

        if (atualizacao.getProcesso() != null) {
            dto.setProcessoId(atualizacao.getProcesso().getId());
        }

        if (atualizacao.getUsuario() != null) {
            dto.setUsuarioId(atualizacao.getUsuario().getId());
            dto.setUsuarioNome(atualizacao.getUsuario().getNome());
        }

        return dto;
    }

    public Atualizacao toEntity() {
        Atualizacao atualizacao = new Atualizacao();
        atualizacao.setId(this.id);
        atualizacao.setDataHora(this.dataHora);
        atualizacao.setDescricao(this.descricao);

        return atualizacao;
    }
}