package com.uade.tpo.marketplace.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

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

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 🔥 AUTH público
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // 🔥 FAVORITOS - SOLO COMPRADOR (va ARRIBA antes de otras reglas)
                        .requestMatchers(HttpMethod.POST, "/api/v1/favorites/**").hasRole("COMPRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/favorites/**").hasRole("COMPRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/favorites/**").hasRole("COMPRADOR")

                        // USUARIOS
                        .requestMatchers(HttpMethod.GET, "/usuarios/").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/usuarios/{id}").hasAnyRole("ADMINISTRADOR", "COMPRADOR")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasAnyRole("ADMINISTRADOR", "COMPRADOR")

                        // PRODUCTOS
                        .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/productos/crear/").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/productos/editar/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/productos/desactivar/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/productos/activar/{id}").hasRole("ADMINISTRADOR")

                        // CARRITO
                        .requestMatchers("/carrito/**").hasRole("COMPRADOR")

                        // COMPRAS
                        .requestMatchers(HttpMethod.POST, "/compras/checkout/").hasRole("COMPRADOR")
                        .requestMatchers(HttpMethod.GET, "/compras/**").hasRole("COMPRADOR")
                        .requestMatchers(HttpMethod.GET, "/compras/todas/").hasRole("ADMINISTRADOR")

                        // CATEGORIAS
                        .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                        .requestMatchers("/categorias/**").hasRole("ADMINISTRADOR")

                        // ATRIBUTOS
                        .requestMatchers("/atributos/**").hasRole("ADMINISTRADOR")

                        // 🔥 CUALQUIER OTRA COSA DEBE ESTAR AUTENTICADA
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
