package com.ifpe.pw_defesa_civil.model.controller;

import com.ifpe.pw_defesa_civil.model.dto.AtualizacaoDTO;
import com.ifpe.pw_defesa_civil.service.AtualizacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AtualizacaoController {

    private final AtualizacaoService atualizacaoService;

    public AtualizacaoController(AtualizacaoService atualizacaoService) {
        this.atualizacaoService = atualizacaoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public List<AtualizacaoDTO> findAll() {
        return atualizacaoService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<AtualizacaoDTO> findById(@PathVariable Long id) {
        Optional<AtualizacaoDTO> atualizacaoDTO = atualizacaoService.findById(id);
        return atualizacaoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<AtualizacaoDTO> create(@RequestBody AtualizacaoDTO atualizacaoDTO) {
        AtualizacaoDTO savedDTO = atualizacaoService.save(atualizacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<AtualizacaoDTO> update(@PathVariable Long id, @RequestBody AtualizacaoDTO atualizacaoDTO) {
        if (atualizacaoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        atualizacaoDTO.setId(id);
        AtualizacaoDTO updatedDTO = atualizacaoService.save(atualizacaoDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (atualizacaoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        atualizacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}