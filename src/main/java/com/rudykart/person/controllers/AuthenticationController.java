package com.rudykart.person.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rudykart.person.dto.AuthenticationDto;
import com.rudykart.person.dto.AuthenticationResponse;
import com.rudykart.person.dto.RegisterDto;
import com.rudykart.person.services.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // tes
    // @PostMapping("/register")
    // public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody
    // RegisterDto request) {
    // return ResponseEntity.ok(authenticationService.register(request));
    // }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@Valid @RequestBody RegisterDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Dapatkan token JWT dari header Authorization
        String authHeader = request.getHeader("Authorization");
        String jwtToken = authHeader.substring(7); // Menghapus "Bearer " dari token

        // Lakukan operasi logout sesuai kebutuhan, misalnya:
        // - Menghapus token dari cache/session
        // - Menandai token sebagai tidak valid di database
        // - Menghapus token dari cookie, dll.

        // Contoh operasi logout sederhana:
        // Menghapus token dari cache/session
        // CacheManager.getInstance().removeToken(jwtToken);

        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/tes")
    public Map<String, String> tes(@RequestBody RegisterDto request) {

        Map<String, String> data = new HashMap<>();

        data.put("name", request.getName());

        return data;
    }
}
