package com.ifpe.pw_defesa_civil.model.controller;

import com.ifpe.pw_defesa_civil.model.entity.Mapa;
import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.service.MapaService;
import com.ifpe.pw_defesa_civil.service.ProcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @GetMapping
    public List<Mapa> findAll() {
        return mapaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mapa> findById(@PathVariable Long id) {
        Optional<Mapa> mapa = mapaService.findById(id);
        return mapa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mapa create(@RequestBody Mapa mapa) {
        return mapaService.save(mapa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mapa> update(@PathVariable Long id, @RequestBody Mapa mapa) {
        if (mapaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mapa.setId(id);
        Mapa updated = mapaService.save(mapa);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (mapaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mapaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
