package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.repository.EquipeRepository;

import jakarta.transaction.Transactional;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    @Transactional
    public List<Equipe> findAll() {
        return equipeRepository.findAll();
    }

    @Transactional
    public Optional<Equipe> findById(Long id) {
        return equipeRepository.findById(id);
    }

    @Transactional
    public Equipe save(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @Transactional
    public void deleteById(Long id) {
        equipeRepository.deleteById(id);
    }
}