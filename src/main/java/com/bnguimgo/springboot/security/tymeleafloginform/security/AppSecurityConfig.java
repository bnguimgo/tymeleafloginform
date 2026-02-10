package com.bnguimgo.springboot.security.tymeleafloginform.security;

import com.bnguimgo.springboot.security.tymeleafloginform.security.succes.RedirectUrlLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

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
/*        Spring Security envoie par défaut plusieurs en-têtes HTTP de sécurité, dont :

        1-) X-Frame-Options → protège contre le clickjacking (--> Permet d’afficher la page dans une <iframe> du même domaine (localhost dans notre cas))

        2-) X-XSS-Protection → protège contre le cross-site scripting (XSS) dans les vieux navigateurs
            Voici les valeurs possibles dans XXssProtectionHeaderWriter.HeaderValue :

            Constante	            En-tête envoyé	                Description
            ENABLED	                X-XSS-Protection: 1	            Active la protection XSS basique
            ENABLED_MODE_BLOCK	    X-XSS-Protection: 1;mode=block	Active la protection et bloque la page si XSS détecté
            DISABLED	            X-XSS-Protection: 0	            Désactive la protection

            NB: L’option X-XSS-Protection n’a plus aucun effet sur les navigateurs modernes (Chrome, Edge, Firefox), mais ne gêne pas non plus.
        */

        http.headers(headers ->
                        headers
                                // Activer la protection XSS
                                .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                                // Autoriser l'affichage dans une iframe depuis la même origine
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/authors/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER")
                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                        .anyRequest()
                        .authenticated())
                //.oauth2Login(Customizer.withDefaults())//FIXME on peut utiliser cette configuration par défaut sans avoir besoin de authenticationSuccessHandler. --> la config ci-dessous est optionnelle
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer
                            .loginPage("/login")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/login?error");
                    httpSecurityOAuth2LoginConfigurer.successHandler(authenticationSuccessHandler);
                })
                //.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout");
                    logout.addLogoutHandler(customLogoutSuccessHandler);
                    logout.logoutSuccessHandler(redirectUrlLogoutHandler);
                    logout.clearAuthentication(true);
                });
        return http.build();
    }
  }