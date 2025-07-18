package com.ifpe.pw_defesa_civil.repository;

import com.ifpe.pw_defesa_civil.model.entity.Processo;
import com.ifpe.pw_defesa_civil.model.enums.StatusProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    @Query("SELECT count(p) FROM Processo p")
    long countTotalProcessos();

    @Query("SELECT count(p) FROM Processo p WHERE p.status = :status")
    long countProcessosPorStatus(@Param("status") StatusProcesso status);
}