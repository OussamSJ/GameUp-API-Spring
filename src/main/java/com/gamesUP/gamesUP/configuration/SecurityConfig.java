package com.gamesUP.gamesUP.configuration;

import com.gamesUP.gamesUP.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthTokenFilter jwtFilter = new JwtAuthTokenFilter(jwtUtil, userDetailsService);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"error\": \"Unauthorized - " + authException.getMessage() + "\" }");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"error\": \"Access denied\" }");
                        })
                )
                .authorizeHttpRequests(auth -> auth

                        //Auth
                        .requestMatchers("/api/auth/**").permitAll()

                        //Games
                        .requestMatchers(HttpMethod.GET,"/api/games").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/games/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/games").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/games/**").hasRole("ADMIN")

                        //Categories
                        .requestMatchers(HttpMethod.GET,"/api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/categories/**").hasRole("ADMIN")

                        //Publishers
                        .requestMatchers(HttpMethod.GET,"/api/publishers").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/publishers/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/publishers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/publishers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/publishers/**").hasRole("ADMIN")

                        //Authors
                        .requestMatchers(HttpMethod.GET,"/api/authors").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/authors/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/authors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/authors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/authors/**").hasRole("ADMIN")

                        //Avis
                        .requestMatchers(HttpMethod.GET,"/api/avis").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/avis/**").permitAll()

                        //Users
                        .requestMatchers("/api/users").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        //Inventories
                        .requestMatchers("/api/inventories").hasRole("ADMIN")
                        .requestMatchers("/api/inventories/**").hasRole("ADMIN")

                        //Inventory-lines
                        .requestMatchers("/api/inventory-lines").hasRole("ADMIN")
                        .requestMatchers("/api/inventory-lines/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
