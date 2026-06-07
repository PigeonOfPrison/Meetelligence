package com.hammad.meetelligence.Service;

import com.hammad.meetelligence.Entity.Dto.Auth.AuthResponse;
import com.hammad.meetelligence.Entity.Dto.Auth.LoginRequest;
import com.hammad.meetelligence.Entity.Dto.Auth.RegisterRequest;
import com.hammad.meetelligence.Entity.User;
import com.hammad.meetelligence.Error.ResourceNotFoundException;
import com.hammad.meetelligence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder , UserRepository userRepository, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<AuthResponse> register(RegisterRequest req) {
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    public ResponseEntity<AuthResponse> login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with mail " + req.getEmail() + "not found")
                );

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Incorrect password"));
        }

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
