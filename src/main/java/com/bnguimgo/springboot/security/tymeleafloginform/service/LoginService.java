package com.bnguimgo.springboot.security.tymeleafloginform.service;

import com.bnguimgo.springboot.security.tymeleafloginform.modele.User;
import com.bnguimgo.springboot.security.tymeleafloginform.security.succes.CustomIDTokenValidator;
import com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.MalformedURLException;
import java.text.ParseException;

@Service
public class LoginService {

    private final RestClient restClient;

    private final CustomIDTokenValidator customIDTokenValidator;

    @Autowired
    public LoginService(RestClient restClient, CustomIDTokenValidator customIDTokenValidator) {
        this.restClient = restClient;
        this.customIDTokenValidator = customIDTokenValidator;
    }

    /**
     *  Méthode nécessaire à l'authentification de l'utilisateur (Login utilisateur)
     * @param email Email de l'utilisateur
     * @return Renvoie les infos sur l'utilisateur
     * @throws MalformedURLException Renvoie une erreur en cas de mauvaise Url
     * @throws ParseException Parse le résultat
     */
    public User loginByEmail(String email) throws MalformedURLException, ParseException {

        String valideIdToken = validateToken(SecurityContextHolder.getContext().getAuthentication());
        return restClient.get()
                .uri("/api/v1/users/{email}", email)
                .header("Authorization", "Bearer " + valideIdToken)
                .retrieve()
                .toEntity(User.class).getBody();
    }

    private String validateToken(Authentication authentication) throws ParseException {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication) {
            throw new ParseException("Authentication required ", 0);
        }
        if(authentication.getPrincipal() instanceof DefaultOidcUser defaultOidcUser) {
            String idToken = defaultOidcUser.getIdToken().getTokenValue();
            return customIDTokenValidator.validate(idToken);
        } else if(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            String idToken = customUserDetails.getIdToken();
            return customIDTokenValidator.validate(idToken);
        } else {
            throw new ParseException("Unknown authentication method ", 0);
        }

    }

    /**
     *  Méthode nécessaire lorsque l'utilisateur est déjà connecté et présent dans le contexte
     * @param email Email de l'utilisateur
     * @return Renvoie les infos sur l'utilisateur
     * @throws ParseException Parse le résultat
     */
    public User findByEmail(String email) throws ParseException {

        String valideIdToken = validateToken(SecurityContextHolder.getContext().getAuthentication());
        return restClient.get()
                .uri("/api/v1/users/{email}", email)
                .header("Authorization", "Bearer " + valideIdToken)
                .retrieve()
                .toEntity(User.class).getBody();
    }
}
