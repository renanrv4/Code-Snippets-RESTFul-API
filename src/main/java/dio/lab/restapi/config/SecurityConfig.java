package dio.lab.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF de forma mais explícita
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()  // Permite o acesso sem autenticação a todos os endpoints
                        .anyRequest().authenticated()  // Requer autenticação para outras rotas (opcional)
                );

        return http.build();
    }
}
