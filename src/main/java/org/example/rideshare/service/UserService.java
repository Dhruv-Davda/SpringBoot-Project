package org.example.rideshare.service;

import org.example.rideshare.dto.AuthResponse;
import org.example.rideshare.dto.LoginRequest;
import org.example.rideshare.dto.RegisterRequest;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.UnauthorizedException;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.UserRepository;
import org.example.rideshare.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public UserService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder, 
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        
        User user = new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getRole()
        );
        
        userRepository.save(user);
        
        return new AuthResponse("User registered successfully");
    }
    
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
        
        return new AuthResponse(token, user.getUsername(), user.getRole());
    }
}
