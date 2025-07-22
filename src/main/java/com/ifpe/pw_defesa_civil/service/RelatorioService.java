package com.ifpe.pw_defesa_civil.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ifpe.pw_defesa_civil.model.dto.RelatorioDTO;
import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.model.entity.Relatorio;
import com.ifpe.pw_defesa_civil.model.entity.Relatorio.TipoConteudo;
import com.ifpe.pw_defesa_civil.repository.ProcessoRepository;
import com.ifpe.pw_defesa_civil.repository.RelatorioRepository;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final ProcessoRepository processoRepository;

    public RelatorioService(RelatorioRepository relatorioRepository,
                            ProcessoRepository processoRepository) {
        this.relatorioRepository = relatorioRepository;
        this.processoRepository = processoRepository;
    }

    public RelatorioDTO salvarRelatorio(MultipartFile conteudo, String tipo, Long idProcesso) throws IOException{
        Processo processo = processoRepository.findById(idProcesso)
                .orElseThrow(() -> new IllegalArgumentException("Processo não encontrado com o ID: " + idProcesso));

        Relatorio relatorio = new Relatorio();
        relatorio.setConteudo(conteudo.getBytes());
        relatorio.setTipoDocumento(conteudo.getContentType());
        relatorio.setNomeDocumento(conteudo.getOriginalFilename());

        relatorio.setTipo(TipoConteudo.valueOf(tipo.toUpperCase()));

        relatorio.setProcesso(processo);

        Relatorio relatorioSalvo = relatorioRepository.save(relatorio);

        return RelatorioDTO.fromEntity(relatorioSalvo);
    }

    public RelatorioDTO buscarRelatorioPorId(Long id) {
        Relatorio relatorio = relatorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Relatório não encontrado com o ID: " + id));

        return RelatorioDTO.fromEntity(relatorio);
    }
}