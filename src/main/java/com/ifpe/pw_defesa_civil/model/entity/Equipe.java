package com.ifpe.pw_defesa_civil.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "equipes")
public class Equipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_equipe", nullable = false, length = 255, unique = true)
    private String nomeEquipe;

    // @Column(name = "data_criacao", nullable = false)
    // private LocalDateTime dataCriacao;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "membrosequipe",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> membros = new ArrayList<>();

    @OneToMany(mappedBy = "equipe")
    @JsonManagedReference("equipe-processo")
    private List<Processo> processos = new ArrayList<>();

    public Equipe() {}

    public Equipe(String nomeEquipe, List<Usuario> membros) {
        this.nomeEquipe = nomeEquipe;
        this.membros = membros;
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

    public List<Usuario> getMembros() {
        return membros;
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros;
    }

    // public LocalDateTime getDataCriacao() {
    //     return dataCriacao;
    // }

    // public void setDataCriacao(LocalDateTime dataCriacao) {
    //     this.dataCriacao = dataCriacao;
    // }

}
