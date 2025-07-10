package com.ifpe.pw_defesa_civil.service;

import com.ifpe.pw_defesa_civil.model.entity.Atualizacao;
import com.ifpe.pw_defesa_civil.model.entity.Mapa;
import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.AtualizacaoRepository;
import com.ifpe.pw_defesa_civil.repository.AtualizacaoRepository;
import com.ifpe.pw_defesa_civil.repository.ProcessoRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

