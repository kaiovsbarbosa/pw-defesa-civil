package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.ifpe.pw_defesa_civil.model.dto.ProcessoDTO;
import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.model.enums.StatusProcesso;
import com.ifpe.pw_defesa_civil.repository.EquipeRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.repository.ProcessoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipeRepository equipeRepository;

    public ProcessoService(ProcessoRepository processoRepository, UsuarioRepository usuarioRepository, EquipeRepository equipeRepository) {
        this.processoRepository = processoRepository;
        this.usuarioRepository = usuarioRepository;
        this.equipeRepository = equipeRepository;
    }

    @Transactional
    public List<ProcessoDTO> findAll() {
        return processoRepository.findAll().stream()
                .map(ProcessoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ProcessoDTO> findById(Long id) {
        return processoRepository.findById(id)
                .map(ProcessoDTO::fromEntity);
    }

    @Transactional
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        Processo processo = processoDTO.toEntity();
        Usuario criadoPor = usuarioRepository.findById(processoDTO.getCriadoPorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + processoDTO.getCriadoPorId()));
        processo.setCriadoPor(criadoPor);

        if (processoDTO.getEquipeId() != null) {
            Equipe equipe = equipeRepository.findById(processoDTO.getEquipeId())
                    .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada com o ID: " + processoDTO.getEquipeId()));
            processo.setEquipe(equipe);
        }

        Processo processoSalvo = processoRepository.save(processo);

        return ProcessoDTO.fromEntity(processoSalvo);
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