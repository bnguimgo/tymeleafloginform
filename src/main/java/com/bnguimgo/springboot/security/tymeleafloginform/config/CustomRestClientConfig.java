package com.bnguimgo.springboot.security.tymeleafloginform.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Déclaration d’un RestClient réutilisable dans toute l'application
 * Voir également spring RestClient vs webClient
 */
@Configuration
@Slf4j
public class CustomRestClientConfig {

    @Autowired
    PropertiesServiceConfig properties;
    @Bean
    public RestClient restClient() {
        log.info("Client-Url = "+ properties.getClientUrl());
        log.info("Server-Url = "+ properties.getServerUrl());
        log.info("Download-certificate-Url = "+ properties.getTokenUri());
        return RestClient.builder()
                .baseUrl(properties.getServerUrl()) // Url API côté serveur
                //.defaultHeader("", "")//On peut ajouter un header avec plusieurs paramètres
                .build();
    }
}