package com.bnguimgo.springboot.security.tymeleafloginform.security;

import com.bnguimgo.springboot.security.tymeleafloginform.config.RedirectUrlLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Source 1: <a href="https://www.javainuse.com/spring/spring-boot-oauth-authorization-code">spring-boot-oauth-authorization-code</a>
 * Source 2: <a href="https://www.baeldung.com/spring-security-thymeleaf">spring-security-thymeleaf</a>
 * Source 3: <a href="https://www.baeldung.com/spring-security-login">spring-security-login</a>
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    LogoutHandler customLogoutSuccessHandler;
    @Autowired
    RedirectUrlLogoutHandler redirectUrlLogoutHandler;
    
    //Filter Chain
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/admins/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/anonymous/**").anonymous()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/homepage", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                        .successHandler(authenticationSuccessHandler)//--> que faire en cas de succès d'authentification ?
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //RedirectUrlLogoutHandler redirectUrlLogoutHandler = new RedirectUrlLogoutHandler();

        http
                //.csrf(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/authors/**").hasAuthority("ADMIN")
                        .requestMatchers("/").permitAll()
                        .anyRequest()
                        .authenticated())
                //.oauth2Login(Customizer.withDefaults())//FIXME on peut utiliser cette configuration par défaut sans avoir besoin de authenticationSuccessHandler. --> la config ci-dessous est optionnelle
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer.loginPage("/");
                    httpSecurityOAuth2LoginConfigurer.successHandler(authenticationSuccessHandler);
                })
                //.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.addLogoutHandler(customLogoutSuccessHandler);
                    logout.logoutSuccessHandler(redirectUrlLogoutHandler);
                    logout.clearAuthentication(true);
                });
        return http.build();
    }
  }