package com.digis01.DGarciaPorgramacionNCapasMarzo25.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    
    // SecurityFilterChain -> @Bean
    // -> autenticar todas las peticiones en todos los endpoints
    // -> el login debe ser accesible para todos 
    // -> la página de redireccion del login debe ser /Alumno

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        
        //Van en orden de arriba hacia abajo
        httpSecurity.authorizeHttpRequests(
        configure -> configure
                .requestMatchers("/Alumno", "/Alumno/CargaMasiva").hasAnyRole("ADMINISTRADOR", "PROGRAMADOR")
                .requestMatchers(HttpMethod.GET, "/Alumno/**").hasAnyRole("ANALISTA", "PROGRAMADOR")
                .requestMatchers("/Alumno/**").hasRole("PROGRAMADOR")
                .anyRequest().authenticated())
                .formLogin(login -> login.permitAll().defaultSuccessUrl("/Alumno"));
        
        return httpSecurity.build();
        
    }    
    
    @Bean InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        
        UserDetails admin = User.builder()
                .username("pepito")
                .password("{noop}pepitopepe")
                .roles("ADMIN")
                .build();
        
        UserDetails analista = User.builder()
                .username("pepinillo")
                .password("{noop}pepinillopicado")
                .roles("ANALISTA")
                .build();
        
        UserDetails programador = User.builder()
                .username("naranja")
                .password("{noop}cascara")
                .roles("PROGRAMADOR")
                .build();
        
        return new InMemoryUserDetailsManager(admin, analista, programador);
    }
     //JdbcUserDetailsManager -> conexión a BD
    // -> InMemoryUserDetailsManager -> @Bean
    // -> 3 usuarios diferentes -> roles diferentes
    
    //Configurar SecurityFilterChain 
    // Programador  -> Puede hacer todo
    // Administrador -> Carga Masiva
    // Analista -> Revisar los detalles de los usuarios y sus direcciones
    
    //password = "{noop}1234";
    
    
    
    
}
