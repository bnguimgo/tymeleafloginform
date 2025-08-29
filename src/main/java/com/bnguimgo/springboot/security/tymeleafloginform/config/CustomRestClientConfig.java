package com.bnguimgo.springboot.security.tymeleafloginform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Déclaration d’un RestClient réutilisable dans toute l'application
 * Voir également spring RestClient vs webClient
 */
@Configuration
public class CustomRestClientConfig {

    @Autowired
    PropertiesServiceConfig properties;
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(properties.getServerUrl()) // Url API côté serveur
                //.defaultHeader("", "")//On peut ajouter un header avec plusieurs paramètres
                .build();
    }
}