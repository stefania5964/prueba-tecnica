package com.example.PruebaTecnica.config;
import com.example.PruebaTecnica.model.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(); // Implementa tu UserDetailsService aquí
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/login").permitAll()
                                .requestMatchers("/empresas").hasAnyRole("ADMIN", "EXTERNO")
                                .requestMatchers("/**").hasRole("ADMIN")
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // Manejar errores de autenticación
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}
    /*@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin@example.com") .password(passwordEncoder().encode("admin123"))
                .roles(Rol.ADMIN.name())
                .build());
        manager.createUser(User.withUsername("externo@example.com")
                .password(passwordEncoder().encode("externo123"))
                .roles(Rol.EXTERNO.name())
                .build());
        return manager;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/login").permitAll()
                                .requestMatchers("/empresas").hasAnyRole("ADMIN", "EXTERNO")
                                .requestMatchers("/**").hasRole("ADMIN")
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginProcessingUrl("/api/login")
                                .defaultSuccessUrl("/empresas", true)
                                .failureUrl("/api/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                );
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/loginx")
                                .defaultSuccessUrl("/visualizarEmpresas")
                                .failureUrl("/loginx?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                );
        return http.build();
    }*/

