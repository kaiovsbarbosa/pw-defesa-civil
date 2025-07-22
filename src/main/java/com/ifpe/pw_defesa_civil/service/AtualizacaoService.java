package com.ifpe.pw_defesa_civil.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ifpe.pw_defesa_civil.model.dto.AtualizacaoDTO;
import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.ProcessoRepository;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.ifpe.pw_defesa_civil.model.entity.Atualizacao;
import com.ifpe.pw_defesa_civil.repository.AtualizacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class AtualizacaoService {
    private final AtualizacaoRepository atualizacaoRepository;
    private final ProcessoRepository processoRepository;
    private final UsuarioRepository usuarioRepository;

    public AtualizacaoService(AtualizacaoRepository atualizacaoRepository, ProcessoRepository processoRepository, UsuarioRepository usuarioRepository) {
        this.atualizacaoRepository = atualizacaoRepository;
        this.processoRepository = processoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public List<AtualizacaoDTO> findAll() {
        return atualizacaoRepository.findAll().stream()
                .map(AtualizacaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<AtualizacaoDTO> findById(Long id) {
        return atualizacaoRepository.findById(id)
                .map(AtualizacaoDTO::fromEntity);
    }

    @Transactional
    public AtualizacaoDTO save(AtualizacaoDTO atualizacaoDTO) {
        Atualizacao atualizacao = atualizacaoDTO.toEntity();

        Long processoId = atualizacaoDTO.getProcessoId();
        if (processoId == null) {
            throw new IllegalArgumentException("O ID do Processo é obrigatório.");
        }
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new EntityNotFoundException("Processo não encontrado com o ID: " + processoId));
        atualizacao.setProcesso(processo);

        Long usuarioId = atualizacaoDTO.getUsuarioId();
        if (usuarioId == null) {
            throw new IllegalArgumentException("O ID do Usuário é obrigatório.");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId));
        atualizacao.setUsuario(usuario);

        Atualizacao atualizacaoSalva = atualizacaoRepository.save(atualizacao);

        return AtualizacaoDTO.fromEntity(atualizacaoSalva);
    }

    @Transactional
    public void deleteById(Long id) {
        atualizacaoRepository.deleteById(id);
    }
}