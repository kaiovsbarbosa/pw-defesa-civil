package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.pw_defesa_civil.model.dto.MapaDTO;
import com.ifpe.pw_defesa_civil.service.MapaService;

@RestController
@RequestMapping("api/mapas")
public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public List<MapaDTO> findAll() {
        return mapaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<MapaDTO> findById(@PathVariable Long id) {
        Optional<MapaDTO> mapaDTO = mapaService.findById(id);
        return mapaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<MapaDTO> create(@RequestBody MapaDTO mapaDTO) {
        MapaDTO savedMapaDTO = mapaService.save(mapaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMapaDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<MapaDTO> update(@PathVariable Long id, @RequestBody MapaDTO mapaDTO) {
        if (mapaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mapaDTO.setId(id);
        MapaDTO updatedDTO = mapaService.save(mapaDTO);
        return ResponseEntity.ok(updatedDTO);
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