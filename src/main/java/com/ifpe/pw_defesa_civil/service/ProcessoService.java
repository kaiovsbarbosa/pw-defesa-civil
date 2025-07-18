package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;

import com.ifpe.pw_defesa_civil.model.enums.StatusProcesso;
import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.repository.ProcessoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;

    public ProcessoService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Transactional
    public List<Processo> findAll() {
        return processoRepository.findAll();
    }

    @Transactional
    public Optional<Processo> findById(Long id) {
        return processoRepository.findById(id);
    }

    @Transactional
    public Processo save(Processo processo) {
        return processoRepository.save(processo);
    }

    @Transactional
    public void deleteById(Long id) {
        processoRepository.deleteById(id);
    }

    @Transactional
    public long getTotalDeProcessos() {
        return processoRepository.countTotalProcessos();
    }

    @Transactional
    public long getTotalProcessosEmAndamento() {
        return processoRepository.countProcessosPorStatus(StatusProcesso.EM_ANDAMENTO);
    }
}
