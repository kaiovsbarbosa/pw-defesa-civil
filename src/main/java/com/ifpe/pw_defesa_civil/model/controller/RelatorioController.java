package com.ifpe.pw_defesa_civil.model.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ifpe.pw_defesa_civil.model.dto.RelatorioDTO;
import com.ifpe.pw_defesa_civil.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @PostMapping
    public ResponseEntity<RelatorioDTO> uploadRelatorio(
            @RequestParam MultipartFile arquivo,
            @RequestParam String tipo,
            @RequestParam Long idProcesso
    ) throws IOException {

        RelatorioDTO relatorioSalvoDTO = relatorioService.salvarRelatorio(arquivo, tipo, idProcesso);

        return ResponseEntity.status(HttpStatus.CREATED).body(relatorioSalvoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadRelatorio(@PathVariable Long id) {
        RelatorioDTO relatorioDTO = relatorioService.buscarRelatorioPorId(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(relatorioDTO.getTipoDocumento()))
                .header("Content-Disposition", "attachment; filename=\"" + relatorioDTO.getNomeDocumento() + "\"")
                .body(relatorioDTO.getConteudo());
    }

}