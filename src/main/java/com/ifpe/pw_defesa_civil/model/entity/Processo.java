package com.ifpe.pw_defesa_civil.model.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ifpe.pw_defesa_civil.model.enums.StatusProcesso;
import com.ifpe.pw_defesa_civil.model.enums.TipoProcesso;
import jakarta.persistence.*;

@Entity
@Table(name = "processos")
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProcesso tipo;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusProcesso status = StatusProcesso.EM_ANDAMENTO;

    @Column(name = "localizacao", columnDefinition = "text")
    private String localizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por_usuario_id", nullable = true)
    @JsonBackReference("usuario-processo")
    private Usuario criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = true)
    @JsonBackReference("equipe-processo")
    private Equipe equipe;

    @Column(name = "equipamento", columnDefinition = "text")
    private String equipamento;

    @Column(name = "descricao", columnDefinition = "text")
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "processo")
    private Relatorio relatorio;

    @Column(name = "arquivo", columnDefinition = "text")
    private String arquivo;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public TipoProcesso getTipo() {
        return tipo;
    }
    public void setTipo(TipoProcesso tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataInicio() {
        return data;
    }
    public void setDataInicio(LocalDateTime dataInicio) {
        this.data = dataInicio;
    }

    public StatusProcesso getStatus() {
        return status;
    }
    public void setStatus(StatusProcesso status) {
        this.status = status;
    }

    public String getLocalizacaoDescricao() {
        return localizacao;
    }
    public void setLocalizacaoDescricao(String localizacaoDescricao) {
        this.localizacao = localizacaoDescricao;
    }

    public Usuario getCriadoPor() {
        return criadoPor;
    }
    public void setCriadoPor(Usuario criadoPor) {
        this.criadoPor = criadoPor;
    }

    public Equipe getEquipe() {
        return equipe;
    }
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEquipamento() {
        return equipamento;
    }
    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }
    public Relatorio getRelatorio() {
        return relatorio;
    }
    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public String getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    public String getArquivo() {
        return arquivo;
    }
    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
}
