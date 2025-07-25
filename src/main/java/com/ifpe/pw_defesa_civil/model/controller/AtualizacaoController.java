package com.ifpe.pw_defesa_civil.model.controller;

import com.ifpe.pw_defesa_civil.model.entity.Atualizacao;
import com.ifpe.pw_defesa_civil.service.AtualizacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class AtualizacaoController {

    private final AtualizacaoService atualizacaoService;

    public AtualizacaoController(AtualizacaoService atualizacaoService) {
        this.atualizacaoService = atualizacaoService;
    }

    @GetMapping
    public List<Atualizacao> findAll() {
        return atualizacaoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atualizacao> findById(@PathVariable Long id) {
        Optional<Atualizacao> mapa = atualizacaoService.findById(id);
        return mapa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Atualizacao create(@RequestBody Atualizacao mapa) {
        return atualizacaoService.save(mapa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atualizacao> update(@PathVariable Long id, @RequestBody Atualizacao mapa) {
        if (atualizacaoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mapa.setId(id);
        Atualizacao updated = atualizacaoService.save(mapa);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (atualizacaoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        atualizacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
