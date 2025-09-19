package com.example.BillingApp.Security;

import com.example.BillingApp.Entity.AppUser;
import com.example.BillingApp.Repository.AppUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepo;

    public CustomUserDetailsService(AppUserRepository repo) {
        this.userRepo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // already encoded
                .roles(user.getRole().replace("ROLE_", "")) // Spring adds ROLE_ prefix automatically
                .build();
    }
}
