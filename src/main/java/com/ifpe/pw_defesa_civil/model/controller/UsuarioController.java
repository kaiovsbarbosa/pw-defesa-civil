package com.ifpe.pw_defesa_civil.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ifpe.pw_defesa_civil.model.dto.AtualizarUsuarioDTO;
import com.ifpe.pw_defesa_civil.model.dto.SalvarUsuarioDTO;
import com.ifpe.pw_defesa_civil.model.dto.UsuarioDTO;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public List<UsuarioDTO> findAll() {
        return usuarioService.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador') OR hasRole('Visualizador')")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public UsuarioDTO create(@RequestBody @Valid SalvarUsuarioDTO usuarioCreateDTO) {
        Usuario usuario = usuarioService.save(usuarioCreateDTO);
        return UsuarioDTO.fromEntity(usuario);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') OR hasRole('Operador')")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id,
            @RequestBody @Valid AtualizarUsuarioDTO usuarioUpdateDTO) {
        Optional<Usuario> usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioAtualizado = usuarioService.update(id, usuarioUpdateDTO);
        return ResponseEntity.ok(UsuarioDTO.fromEntity(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}