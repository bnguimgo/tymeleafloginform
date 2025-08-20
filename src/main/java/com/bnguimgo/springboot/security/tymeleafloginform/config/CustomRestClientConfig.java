package com.bnguimgo.springboot.security.tymeleafloginform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Déclaration d’un RestClient réutilisable dans toute l'application
 * Voir également spring RestClient vs webClient
 */
@Configuration
public class CustomRestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8094/bibliocentrale") // Url API côté serveur
                //.defaultHeader("", "")//On peut ajouter un header avec plusieurs paramètres
                .build();
    }
}