package com.ifpe.pw_defesa_civil.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;
import com.ifpe.pw_defesa_civil.service.TokenService;
import com.ifpe.pw_defesa_civil.util.TokenBlackList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroTokenAcesso extends OncePerRequestFilter{
    
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final TokenBlackList tokenBlackList;

    public FiltroTokenAcesso(TokenService tokenService, UsuarioRepository usuarioRepository, TokenBlackList tokenBlackList) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.tokenBlackList = tokenBlackList;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarTokenRequisicao(request);

        if(token != null && !tokenBlackList.isBlacklisted(token)){
            String email = tokenService.verificarToken(token);
            Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElseThrow();

            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarTokenRequisicao(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
