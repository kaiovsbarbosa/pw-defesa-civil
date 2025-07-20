package com.ifpe.pw_defesa_civil.model.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ifpe.pw_defesa_civil.model.entity.Relatorio;
import com.ifpe.pw_defesa_civil.model.entity.Relatorio.TipoConteudo;
import com.ifpe.pw_defesa_civil.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @PostMapping
    public Relatorio uploadRelatorio(
            @RequestParam MultipartFile arquivo,
            @RequestParam TipoConteudo tipo,
            @RequestParam Long proprietarioId
            ) throws IOException {
        return relatorioService.salvarRelatorio(arquivo, tipo, proprietarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadRelatorio(@PathVariable Long id) {
        Relatorio relatorio = relatorioService.buscarRelatorioPorId(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(relatorio.getTipoDocumento()))
                .header("Content-Disposition", "attachment; filename=\"" + relatorio.getNomeDocumento() + "\"")
                .body(relatorio.getConteudo());
    }
    
}
