package com.ifpe.pw_defesa_civil.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipes")
public class Equipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_equipe", nullable = false, length = 255, unique = true)
    private String nomeEquipe;

    @JsonBackReference
    @ManyToMany(mappedBy = "equipes", fetch = FetchType.LAZY)
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

}
