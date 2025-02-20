package org.example.voicespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.voicespringboot.dto.LoginDto;
import org.example.voicespringboot.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")

public class AuthenticationController {


    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public ResponseEntity<LoginDto> login(@AuthenticationPrincipal OidcUser user) {
        LoginDto loginDto = authenticationService.login(user);
        return ResponseEntity.ok(loginDto);
    }
    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> getToken(@AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(user.getAttributes());
    }



}
