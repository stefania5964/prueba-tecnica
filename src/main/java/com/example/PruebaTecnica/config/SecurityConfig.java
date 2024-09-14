package com.example.PruebaTecnica.config;
import com.example.PruebaTecnica.model.Rol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles(Rol.ADMIN.name())
                .build());
        manager.createUser(User.withUsername("externo")
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
                                .requestMatchers("/**").permitAll()
                                /*.requestMatchers("/empresa/**").hasRole(Rol.ADMIN.name())
                                .requestMatchers("/productos/**").hasRole(Rol.ADMIN.name())
                                .requestMatchers("/inventario/**").hasRole(Rol.ADMIN.name())
                                .requestMatchers("/visualizarEmpresas/**").hasRole(Rol.EXTERNO.name())*/
                                //.requestMatchers("/**").denyAll()
                );
                /*.formLogin(formLogin ->
                        formLogin
                                .loginPage("/loginx")
                                .defaultSuccessUrl("/visualizarEmpresas")
                                .failureUrl("/loginx?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                );*/
        return http.build();
    }
}
