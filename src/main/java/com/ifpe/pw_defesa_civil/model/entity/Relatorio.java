package com.ifpe.pw_defesa_civil.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "relatorios")
public class Relatorio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", nullable = false, length = 255)
    private TipoConteudo tipo;

    @Column(name= "tipoDocumento", nullable = false, length = 255)
    private String tipoDocumento; //application/pdf", "image/png"  

    @Column(name = "nomeDocumento", nullable = false, length = 255)
    private String nomeDocumento; //nome do arquivo, ex: relatorio.pdf, mosaico.png

    @Lob
    @Column(name = "conteudo", columnDefinition = "LONGBLOB")
    private byte[] conteudo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id", referencedColumnName = "id")
    private Processo processo;

    public enum TipoConteudo {
        RELATORIO,
        MOSAICO,
        OUTRO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoConteudo getTipo() {
        return tipo;
    }

    public void setTipo(TipoConteudo tipo) {
        this.tipo = tipo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }
}
