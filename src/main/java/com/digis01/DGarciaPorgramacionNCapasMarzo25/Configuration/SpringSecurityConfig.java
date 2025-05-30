
package com.digis01.DGarciaPorgramacionNCapasMarzo25.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    // SecurityFilterChain -> @Bean
    // -> autenticar todas las peticiones en todos los endpoints
    // -> el login debe ser accesible para todos 
    // -> la página de redireccion del login debe ser /Alumno
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //Roles -> ROLE_PROGRAMADOR
        //Autoridades -> PROGRAMADOR
        //Se debe dar acceso a la URL base a todos
        //Van en orden de arriba hacia abajo
        httpSecurity.authorizeHttpRequests(
                configure -> configure
                        .requestMatchers("/Alumno").hasAnyAuthority("1er Semestre", "2do Semestre", "3er Semestre")
                        .requestMatchers("/Alumno/CargaMasiva", "/Alumno/CargaMasiva/**").hasAnyAuthority("2do Semestre", "3er Semestre")
                        .requestMatchers(HttpMethod.GET, "/Alumno/**").hasAnyAuthority("1er Semestre", "3er Semestre")
                        .requestMatchers("/Alumno/**").hasAuthority("3er Semestre")
                        .anyRequest().authenticated())
                .formLogin(login -> login.permitAll().defaultSuccessUrl("/Alumno"));

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //Autenticar por defecto -> select username, password, enable from user where username = ?;
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, status from alumno where username = ?");

        //Autorizar por defecto -> select username, authorities from authority where username = ?;
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, nombresemestre from semestremanager where username = ?");

        return jdbcUserDetailsManager;

        //RolManager 
        //IdRolManager -> PK
        //IdAlumno -> FK 
        //Username
        //IdRol -> FK
        //NombreRol
    }

    // DelegatingPasswordEncoder-> {noop}, {bcrypt}, {sha256}, {argon2}, {pdfk2}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
