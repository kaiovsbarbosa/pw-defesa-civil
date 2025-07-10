package com.ifpe.pw_defesa_civil.service;

import com.ifpe.pw_defesa_civil.model.entity.Mapa;
import com.ifpe.pw_defesa_civil.repository.MapaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapaService {

    private final MapaRepository mapaRepository;

    public MapaService(MapaRepository mapaRepository) {
        this.mapaRepository = mapaRepository;
    }

    @Transactional
    public List<Mapa> findAll() {
        return mapaRepository.findAll();
    }

    @Transactional
    public Optional<Mapa> findById(Long id) {
        return mapaRepository.findById(id);
    }

    @Transactional
    public Mapa save(Mapa mapa) {
        return mapaRepository.save(mapa);
    }

    @Transactional
    public void deleteById(Long id) {
        mapaRepository.deleteById(id);
    }
}
