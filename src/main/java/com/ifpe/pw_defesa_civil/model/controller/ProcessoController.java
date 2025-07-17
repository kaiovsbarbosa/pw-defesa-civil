package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.service.ProcessoService;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {

    private final ProcessoService processoService;

    public ProcessoController(ProcessoService processoService) {
        this.processoService = processoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public List<Processo> findAll() {
        return processoService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<Processo> findById(@PathVariable Long id) {
        Optional<Processo> processo = processoService.findById(id);
        return processo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public Processo create(@RequestBody Processo processo) {
        return processoService.save(processo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<Processo> update(@PathVariable Long id, @RequestBody Processo processo) {
        if (processoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        processo.setId(id);
        Processo updated = processoService.save(processo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (processoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        processoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}