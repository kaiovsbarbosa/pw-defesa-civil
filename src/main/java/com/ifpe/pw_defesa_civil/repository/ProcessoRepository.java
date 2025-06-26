package com.ifpe.pw_defesa_civil.repository;

import com.ifpe.pw_defesa_civil.model.entity.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}
