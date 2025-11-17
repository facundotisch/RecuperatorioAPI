package com.uade.tpo.marketplace.configs;

import com.uade.tpo.marketplace.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // AUTH - Público
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // USUARIOS
                        .requestMatchers(HttpMethod.GET, "/usuarios/").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/usuarios/{id}").hasAnyRole("ADMINISTRADOR", "COMPRADOR")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasAnyRole("ADMINISTRADOR", "COMPRADOR")

                        // PRODUCTOS - Público (solo lectura)
                        .requestMatchers(HttpMethod.GET, "/productos/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/productos/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/productos/categoria/**").permitAll()

                        // PRODUCTOS - Creación y edición (solo admin)
                        .requestMatchers(HttpMethod.POST, "/productos/crear/").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/productos/editar/{id}").hasRole("ADMINISTRADOR")

                        // PRODUCTOS - Cambio de estado
                        .requestMatchers(HttpMethod.POST, "/productos/desactivar/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/productos/activar/{id}").hasRole("ADMINISTRADOR")

                        // PRODUCTOS - Admin puede ver todos (activos e inactivos)
                        .requestMatchers(HttpMethod.GET, "/productos/todos/").hasRole("ADMINISTRADOR")

                        // CARRITO - Solo compradores
                        .requestMatchers("/carrito/**").hasRole("COMPRADOR")

                        // COMPRAS - Solo compradores (sus propias compras)
                        .requestMatchers(HttpMethod.GET, "/compras/").hasRole("COMPRADOR")
                        .requestMatchers(HttpMethod.GET, "/compras/{id}").hasRole("COMPRADOR")
                        .requestMatchers(HttpMethod.POST, "/compras/checkout/").hasRole("COMPRADOR")

                        // COMPRAS - Admin (ver todas las compras)
                        .requestMatchers(HttpMethod.GET, "/compras/todas/").hasRole("ADMINISTRADOR")

                        // CATEGORIAS - Público (solo lectura)
                        .requestMatchers(HttpMethod.GET, "/categorias/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categorias/{id}").permitAll()

                        // CATEGORIAS - Solo Admin (gestión) - Misma estructura
                        .requestMatchers(HttpMethod.POST, "/categorias/crear/").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/categorias/editar/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/categorias/desactivar/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/categorias/activar/{id}").hasRole("ADMINISTRADOR")

                        // ATRIBUTOS - Solo Admin
                        .requestMatchers("/atributos/").hasRole("ADMINISTRADOR")
                        .requestMatchers("/atributos/**").hasRole("ADMINISTRADOR")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}