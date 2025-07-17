package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ifpe.pw_defesa_civil.model.entity.Mapa;
import com.ifpe.pw_defesa_civil.service.MapaService;

public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public List<Mapa> findAll() {
        return mapaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<Mapa> findById(@PathVariable Long id) {
        Optional<Mapa> mapa = mapaService.findById(id);
        return mapa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public Mapa create(@RequestBody Mapa mapa) {
        return mapaService.save(mapa);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<Mapa> update(@PathVariable Long id, @RequestBody Mapa mapa) {
        if (mapaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mapa.setId(id);
        Mapa updated = mapaService.save(mapa);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (mapaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mapaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
