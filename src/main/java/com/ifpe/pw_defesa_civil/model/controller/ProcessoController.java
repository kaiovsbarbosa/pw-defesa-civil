package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import com.ifpe.pw_defesa_civil.model.dto.ProcessoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<ProcessoDTO> findAll() {
        return processoService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<ProcessoDTO> findById(@PathVariable Long id) {
        Optional<ProcessoDTO> processoDTO = processoService.findById(id);
        return processoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<ProcessoDTO> create(@RequestBody ProcessoDTO processoDTO) {
        ProcessoDTO savedProcessoDTO = processoService.save(processoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProcessoDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<ProcessoDTO> update(@PathVariable Long id, @RequestBody ProcessoDTO processoDTO) {
        if (processoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        processoDTO.setId(id);
        ProcessoDTO updatedDTO = processoService.save(processoDTO);
        return ResponseEntity.ok(updatedDTO);
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

    @GetMapping("/total")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<Long> getTotalProcessos() {
        long total = processoService.getTotalDeProcessos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/total/abertos")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<Long> getTotalProcessosAbertos() {
        long totalAbertos = processoService.getTotalProcessosEmAndamento();
        return ResponseEntity.ok(totalAbertos);
    }
}