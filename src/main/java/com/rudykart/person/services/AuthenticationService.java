package com.rudykart.person.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rudykart.person.dto.AuthenticationDto;
import com.rudykart.person.dto.AuthenticationResponse;
import com.rudykart.person.dto.RegisterDto;
import com.rudykart.person.models.entities.Role;
import com.rudykart.person.models.entities.User;
import com.rudykart.person.models.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterDto register(RegisterDto registerDto) {
        User user = User.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.valueOf(registerDto.getRole()))
                .build();
        userRepository.save(user);
        return registerDto;
    }

    public AuthenticationResponse authenticate(AuthenticationDto authenticationDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getEmail(),
                        authenticationDto.getPassword()));
        var user = userRepository.findByEmail(authenticationDto.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
