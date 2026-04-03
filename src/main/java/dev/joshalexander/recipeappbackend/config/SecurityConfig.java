package dev.joshalexander.recipeappbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${SECURITY_USERNAME:admin}")
  private String username;

  @Value("${SECURITY_PASSWORD:password}")
  private String password;

  @Value("${SECURITY_USERNAME_2:dave}")
  private String username2;

  @Value("${SECURITY_PASSWORD_2:password}")
  private String password2;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // allow CORS preflight
            .requestMatchers("/api/auth/todoist/callback").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(
        "http://localhost:4200",
        "https://joshalexander.dev",
        "https://www.joshalexander.dev"
    ));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
    config.setAllowCredentials(true); // required for Basic auth headers to be sent
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user1 = User.builder()
        .username(username)
        .password(passwordEncoder().encode(password))
        .roles("USER")
        .build();

    UserDetails user2 = User.builder()
        .username(username2)
        .password(passwordEncoder().encode(password2))
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(user1, user2);
  }
}