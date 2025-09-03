package org.project.java.final_project_spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/games/create", "/games/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/games/**").hasAuthority("ADMIN")
                .requestMatchers("/genres", "/genres/create").hasAuthority("ADMIN")
                .requestMatchers("/devs", "/devs/create").hasAuthority("ADMIN")
                .requestMatchers("/games", "/games/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/**").permitAll())

                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .exceptionHandling(Customizer.withDefaults())
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
