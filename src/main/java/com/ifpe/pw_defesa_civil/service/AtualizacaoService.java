package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.entity.Atualizacao;
import com.ifpe.pw_defesa_civil.repository.AtualizacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class AtualizacaoService {
    private final AtualizacaoRepository atualizacaoRepository;

    public AtualizacaoService(AtualizacaoRepository atualizacaoRepository) {
        this.atualizacaoRepository = atualizacaoRepository;
    }

    @Transactional
    public List<Atualizacao> findAll() {
        return atualizacaoRepository.findAll();
    }

    @Transactional
    public Optional<Atualizacao> findById(Long id) {
        return atualizacaoRepository.findById(id);
    }

    @Transactional
    public Atualizacao save(Atualizacao atualizacao) {
        return atualizacaoRepository.save(atualizacao);
    }

    @Transactional
    public void deleteById(Long id) {
        atualizacaoRepository.deleteById(id);
    }
}

