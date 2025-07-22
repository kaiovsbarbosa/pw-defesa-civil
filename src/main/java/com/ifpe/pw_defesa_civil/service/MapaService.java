package com.ifpe.pw_defesa_civil.service;

import com.ifpe.pw_defesa_civil.model.dto.MapaDTO;
import com.ifpe.pw_defesa_civil.model.entity.Mapa;
import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.repository.MapaRepository;
import com.ifpe.pw_defesa_civil.repository.ProcessoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MapaService {

    private final MapaRepository mapaRepository;
    private final ProcessoRepository processoRepository;

    public MapaService(MapaRepository mapaRepository, ProcessoRepository processoRepository) {
        this.mapaRepository = mapaRepository;
        this.processoRepository = processoRepository;
    }

    @Transactional
    public List<MapaDTO> findAll() {
        return mapaRepository.findAll().stream()
                .map(MapaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<MapaDTO> findById(Long id) {
        return mapaRepository.findById(id)
                .map(MapaDTO::fromEntity);
    }

    @Transactional
    public MapaDTO save(MapaDTO mapaDTO) {
        Mapa mapa = mapaDTO.toEntity();
        if (mapaDTO.getProcessoId() != null) {
            Processo processo = processoRepository.findById(mapaDTO.getProcessoId())
                    .orElseThrow(() -> new EntityNotFoundException("Processo não encontrado com o ID: " + mapaDTO.getProcessoId()));
            mapa.setProcesso(processo);
        } else {
            throw new IllegalArgumentException("O ID do Processo é obrigatório para salvar um Mapa.");
        }
        Mapa mapaSalvo = mapaRepository.save(mapa);
        return MapaDTO.fromEntity(mapaSalvo);
    }

    @Transactional
    public void deleteById(Long id) {
        mapaRepository.deleteById(id);
    }
}