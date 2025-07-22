package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ifpe.pw_defesa_civil.model.dto.EquipeDTO;
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
    public List<EquipeDTO> findAll() {
        return equipeService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<EquipeDTO> findById(@PathVariable Long id) {
        Optional<EquipeDTO> equipeDTO = equipeService.findById(id);

        return equipeDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<EquipeDTO> create(@RequestBody EquipeDTO equipeDTO) {
        EquipeDTO savedEquipeDTO = equipeService.save(equipeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipeDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<EquipeDTO> update(@PathVariable Long id, @RequestBody EquipeDTO equipeDTO) {
        if (equipeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipeDTO.setId(id);
        EquipeDTO updatedDTO = equipeService.save(equipeDTO);
        return ResponseEntity.ok(updatedDTO);
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