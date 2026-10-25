package com.trustvault.trustvault;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.trustvault.trustvault.model.User;
import com.trustvault.trustvault.repository.UserRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if(repo.findByUsername("admin").isEmpty()) {
                User u = new User();
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin123"));
                repo.save(u);
                System.out.println("Default user admin/admin123 created");
            }
        };
    }
}
