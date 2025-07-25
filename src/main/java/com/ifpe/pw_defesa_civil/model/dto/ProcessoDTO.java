package com.ifpe.pw_defesa_civil.model.dto;

import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.model.enums.StatusProcesso;
import com.ifpe.pw_defesa_civil.model.enums.TipoProcesso;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class ProcessoDTO {

    private Long id;
    private String tipo;
    private LocalDateTime data;
    private String status;
    private String localizacao;
    private String equipamento;
    private String descricao;
    private String arquivo;
    private Long criadoPorId;
    private Long equipeId;
    private Long relatorioId;

    private String criadoPorNome;
    private String equipeNome;

    public ProcessoDTO() {
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCriadoPorId() {
        return criadoPorId;
    }

    public void setCriadoPorId(Long criadoPorId) {
        this.criadoPorId = criadoPorId;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    public Long getRelatorioId() {
        return relatorioId;
    }

    public void setRelatorioId(Long relatorioId) {
        this.relatorioId = relatorioId;
    }

    public String getCriadoPorNome() {
        return criadoPorNome;
    }

    public void setCriadoPorNome(String criadoPorNome) {
        this.criadoPorNome = criadoPorNome;
    }

    public String getEquipeNome() {
        return equipeNome;
    }

    public void setEquipeNome(String equipeNome) {
        this.equipeNome = equipeNome;
    }

    public static ProcessoDTO fromEntity(Processo processo) {
        ProcessoDTO dto = new ProcessoDTO();
        dto.setId(processo.getId());
        dto.setData(processo.getDataInicio());
        dto.setLocalizacao(processo.getLocalizacaoDescricao());
        dto.setEquipamento(processo.getEquipamento());
        dto.setDescricao(processo.getDescricao());
        dto.setArquivo(processo.getArquivo());

        if (processo.getTipo() != null) {
            dto.setTipo(processo.getTipo().name());
        }
        if (processo.getStatus() != null) {
            dto.setStatus(processo.getStatus().name());
        }

        if (processo.getCriadoPor() != null) {
            dto.setCriadoPorId(processo.getCriadoPor().getId());
            dto.setCriadoPorNome(processo.getCriadoPor().getNome());
        }

        if (processo.getEquipe() != null) {
            dto.setEquipeId(processo.getEquipe().getId());
            // Supondo que a entidade Equipe tenha um método getNome()
            dto.setEquipeNome(processo.getEquipe().getNomeEquipe());
        }

        if (processo.getRelatorio() != null) {
            dto.setRelatorioId(processo.getRelatorio().getId());
        }

        return dto;
    }

    public Processo toEntity() {
        Processo processo = new Processo();
        processo.setId(this.id);
        processo.setDataInicio(this.data);
        processo.setLocalizacaoDescricao(this.localizacao);
        processo.setEquipamento(this.equipamento);
        processo.setDescricao(this.descricao);
        processo.setArquivo(this.arquivo);

        try {
            if (this.tipo != null) {
                processo.setTipo(TipoProcesso.valueOf(this.tipo.toUpperCase()));
            }
            if (this.status != null) {
                processo.setStatus(StatusProcesso.valueOf(this.status.toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Valor de enum inválido fornecido no DTO: " + e.getMessage());
        }

        return processo;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
}