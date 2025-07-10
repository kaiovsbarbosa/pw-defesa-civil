package com.ifpe.pw_defesa_civil.repository;

import com.ifpe.pw_defesa_civil.model.entity.Atualizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtualizacaoRepository extends JpaRepository<Atualizacao, Long> {}
