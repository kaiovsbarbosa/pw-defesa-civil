package com.ifpe.pw_defesa_civil.util;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ifpe.pw_defesa_civil.service.TokenService;

@Component
public class TokenBlackList {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenBlackList.class);

    private final TokenService tokenService;

    public TokenBlackList(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private Set<String> blacklist = new HashSet<>();

    public void addToBlacklist(String token) {
        LOGGER.info(" - - - - - Adicionando token a lista negra - - - - - ");
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    @Scheduled(fixedRate = 3600000)  
    public void limparListaNegra() {
        LOGGER.info(" - - - - - Limpando lista negra de tokens expirados - - - - - ");
        blacklist.removeIf(token -> tokenService.isTokenExpired(token));
    }
}
