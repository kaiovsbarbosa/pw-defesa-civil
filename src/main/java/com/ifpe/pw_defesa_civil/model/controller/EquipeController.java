package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ifpe.pw_defesa_civil.model.entity.Equipe;
import com.ifpe.pw_defesa_civil.service.EquipeService;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public List<Equipe> findAll() {
        return equipeService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<Equipe> findById(@PathVariable Long id) {
        Optional<Equipe> equipe = equipeService.findById(id);
        return equipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public Equipe create(@RequestBody Equipe equipe) {
        return equipeService.save(equipe);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<Equipe> update(@PathVariable Long id, @RequestBody Equipe equipe) {
        if (equipeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipe.setId(id);
        Equipe updated = equipeService.save(equipe);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (equipeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}