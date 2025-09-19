package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.AppUser;
import com.example.BillingApp.Repository.AppUserRepository;
import com.example.BillingApp.Security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final AppUserRepository userRepo;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, PasswordEncoder encoder, AppUserRepository repo) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.userRepo = repo;
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole("ROLE_USER");
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.get("username"), req.get("password"))
        );
        String token = jwtUtil.generateToken(req.get("username"));
        return Map.of("token", token);
    }
}
