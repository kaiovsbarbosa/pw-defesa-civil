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

    @Column(name = "data_inicio", nullable = false) //considerar apenas como data
    private LocalDateTime dataInicio;

    @Column(name = "data_fim") //desconsiderar
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusProcesso status = StatusProcesso.EM_ANDAMENTO;

    @Column(name = "localizacao_descricao", columnDefinition = "text") //mudar para local/localizacao
    private String localizacaoDescricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por_usuario_id", nullable = false)
    @JsonBackReference
    private Usuario criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id")
    @JsonBackReference
    private Equipe equipe;

    @Column(name = "equipamento", columnDefinition = "text")
    private String equipamento;

    @Column(name = "descricao", columnDefinition = "text")
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "processo")
    private Relatorio relatorio;

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
        return dataInicio;
    }
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }
    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public StatusProcesso getStatus() {
        return status;
    }
    public void setStatus(StatusProcesso status) {
        this.status = status;
    }

    public String getLocalizacaoDescricao() {
        return localizacaoDescricao;
    }
    public void setLocalizacaoDescricao(String localizacaoDescricao) {
        this.localizacaoDescricao = localizacaoDescricao;
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
    // public String getCidadeBairro() {
    //     return cidadeBairro;
    // }
    // public void setCidadeBairro(String cidadeBairro) {
    //     this.cidadeBairro = cidadeBairro;
    // }
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
}
