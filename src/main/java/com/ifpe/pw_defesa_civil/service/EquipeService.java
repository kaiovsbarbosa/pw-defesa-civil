package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.dto.EquipeDTO;
import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.EquipeRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final UsuarioRepository usuarioRepository;

    public EquipeService(EquipeRepository equipeRepository, UsuarioRepository usuarioRepository) {
        this.equipeRepository = equipeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public List<EquipeDTO> findAll() {
        List<Equipe> equipes = equipeRepository.findAll();
        return equipes.stream()
                .map(EquipeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<EquipeDTO> findById(Long id) {
        Optional<Equipe> equipeOptional = equipeRepository.findById(id);
        return equipeOptional.map(EquipeDTO::fromEntity);
    }

    @Transactional
    public EquipeDTO save(EquipeDTO equipeDTO) {
        Equipe equipe = equipeDTO.toEntity();

        if (equipeDTO.getMembrosIds() != null && !equipeDTO.getMembrosIds().isEmpty()) {
            List<Usuario> membros = usuarioRepository.findAllById(equipeDTO.getMembrosIds());
            if(membros.size() != equipeDTO.getMembrosIds().size()){
                throw new IllegalArgumentException("Um ou mais IDs de membros são inválidos.");
            }
            equipe.setMembros(membros);
        }

        Equipe equipeSalva = equipeRepository.save(equipe);

        return EquipeDTO.fromEntity(equipeSalva);
    }

    @Transactional
    public void deleteById(Long id) {
        equipeRepository.deleteById(id);
    }
}