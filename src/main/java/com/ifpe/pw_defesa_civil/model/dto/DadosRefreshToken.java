package com.ifpe.pw_defesa_civil.model.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosRefreshToken(
    @NotBlank String refreshToken
) {
    
}
