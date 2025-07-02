package com.ifpe.pw_defesa_civil.repository;

import com.ifpe.pw_defesa_civil.model.entity.Mapa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapaRepository extends JpaRepository<Mapa, Long> { }
