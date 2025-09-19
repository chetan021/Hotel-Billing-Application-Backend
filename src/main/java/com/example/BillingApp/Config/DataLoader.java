package com.example.BillingApp.Config;

import com.example.BillingApp.Enum.Role;
import com.example.BillingApp.Entity.AppUser;
import com.example.BillingApp.Repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(AppUserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                AppUser u = new AppUser();
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin123"));
                u.setRole(String.valueOf(Role.ADMIN));
                userRepo.save(u);
                System.out.println("Created default admin: admin/admin123");
            }
        };
    }
}
