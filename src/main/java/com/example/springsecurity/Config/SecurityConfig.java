package com.example.springsecurity.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                        .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/authenticated", true)
                        .permitAll();
//            http.formLogin(form -> form
//                            .loginPage("/login")
//                            .permitAll()
//                    );
        http.rememberMe();
        http.sessionManagement();
            http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/profile").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/adminView").hasRole("ADMIN")
                        .requestMatchers("/customer").hasAnyRole("USER")
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic();
                return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User //.withDefaultPasswordEncoder()
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails user1 = User //.withDefaultPasswordEncoder()
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails user2 = User
                .withUsername("root")
                .password(passwordEncoder().encode("root"))
                .roles("USER", "ADMIN")
                .build();
//        List<UserDetails> users = new ArrayList<>();
//        users.add(user);
//        users.add(user1);
        return new InMemoryUserDetailsManager(user,user1, user2);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeRequests(auth -> auth
//                        .antMatchers("/home").permitAll()
//                        .antMatchers("/login").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(withDefaults())
//                .build();
//
//    }

}
