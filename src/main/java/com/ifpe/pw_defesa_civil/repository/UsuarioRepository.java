package com.ifpe.pw_defesa_civil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.pw_defesa_civil.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
