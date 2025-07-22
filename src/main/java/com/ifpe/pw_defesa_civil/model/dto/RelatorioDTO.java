package com.ifpe.pw_defesa_civil.model.dto;

import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.model.entity.Relatorio;
import com.ifpe.pw_defesa_civil.model.entity.Relatorio.TipoConteudo;

public class RelatorioDTO {

    private Long id;
    private String tipo;
    private String tipoDocumento;
    private String nomeDocumento;
    private byte[] conteudo;
    private Long processoId;

    public RelatorioDTO() {
    }

    public RelatorioDTO(Long id, String tipo, String tipoDocumento, String nomeDocumento, byte[] conteudo, Long processoId) {
        this.id = id;
        this.tipo = tipo;
        this.tipoDocumento = tipoDocumento;
        this.nomeDocumento = nomeDocumento;
        this.conteudo = conteudo;
        this.processoId = processoId;
    }

    // Getters e Setters

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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public static RelatorioDTO fromEntity(Relatorio relatorio) {
        RelatorioDTO dto = new RelatorioDTO();
        dto.setId(relatorio.getId());
        dto.setTipoDocumento(relatorio.getTipoDocumento());
        dto.setNomeDocumento(relatorio.getNomeDocumento());
        dto.setConteudo(relatorio.getConteudo());

        if (relatorio.getTipo() != null) {
            dto.setTipo(relatorio.getTipo().name());
        }

        if (relatorio.getProcesso() != null) {
            dto.setProcessoId(relatorio.getProcesso().getId());
        }

        return dto;
    }

    public Relatorio toEntity() {
        Relatorio relatorio = new Relatorio();
        relatorio.setId(this.id);
        relatorio.setTipoDocumento(this.tipoDocumento);
        relatorio.setNomeDocumento(this.nomeDocumento);
        relatorio.setConteudo(this.conteudo);

        if (this.tipo != null) {
            try {
                relatorio.setTipo(TipoConteudo.valueOf(this.tipo.toUpperCase()));
            } catch (IllegalArgumentException e) {
                relatorio.setTipo(null);
            }
        }

        return relatorio;
    }
}