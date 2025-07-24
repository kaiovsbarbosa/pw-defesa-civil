package com.ifpe.pw_defesa_civil.model.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.pw_defesa_civil.model.dto.DadosLogin;
import com.ifpe.pw_defesa_civil.model.dto.DadosRefreshToken;
import com.ifpe.pw_defesa_civil.model.dto.DadosToken;
import com.ifpe.pw_defesa_civil.model.entity.Usuario;
import com.ifpe.pw_defesa_civil.repository.UsuarioRepository;
import com.ifpe.pw_defesa_civil.service.TokenService;
import com.ifpe.pw_defesa_civil.util.TokenBlackList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class AutenticacaoController {
    
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final TokenBlackList tokenBlackList;

    public AutenticacaoController(AuthenticationManager authenticationManager,
                                  TokenService tokenService,
                                  UsuarioRepository usuarioRepository,
                                  TokenBlackList tokenBlackList){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.tokenBlackList = tokenBlackList;
    }

    @PostMapping("/login")
    public ResponseEntity<DadosToken> efetuarLogin(@Valid @RequestBody DadosLogin dados,
        HttpServletResponse response){
        System.out.println(usuarioRepository.findByEmailIgnoreCase("adm@email.com"));
        var autenticationToken = new UsernamePasswordAuthenticationToken(dados.usuario(), dados.senha());
        var authentication = authenticationManager.authenticate(autenticationToken);

        String tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        ResponseCookie cookie = ResponseCookie
            .from("JWT", tokenAcesso)
            .httpOnly(true)
            .secure(true) 
            .sameSite("Strict") 
            .path("/")
            .maxAge(Duration.ofMinutes(720))                    
            .build();                                            

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new DadosToken(tokenAcesso, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> efetuarLogout(HttpServletResponse response,
                @CookieValue("JWT") String jwt){
        if (jwt != null) {
            tokenBlackList.addToBlacklist(jwt);
        }
        ResponseCookie cookie = ResponseCookie.from("JWT", "")
            .path("/")
            .maxAge(0)          
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<DadosToken> atualizarToken(@Valid @RequestBody DadosRefreshToken dados){
        var refreshToken = dados.refreshToken();
        Long idUsuario = Long.valueOf(tokenService.verificarToken(refreshToken));
        var usuario = usuarioRepository.findById(idUsuario).orElseThrow();

        String tokenAcesso = tokenService.gerarToken(usuario);
        String tokenAtualizacao = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok(new DadosToken(tokenAcesso, tokenAtualizacao));
    }

    private String recuperarTokenRequisicao(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
