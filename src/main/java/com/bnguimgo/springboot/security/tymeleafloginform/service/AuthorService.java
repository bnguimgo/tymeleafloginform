package com.bnguimgo.springboot.security.tymeleafloginform.service;

import com.bnguimgo.springboot.security.tymeleafloginform.dto.AuthorDTO;
import com.bnguimgo.springboot.security.tymeleafloginform.security.succes.CustomIDTokenValidator;
import com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.MalformedURLException;
import java.text.ParseException;

@Service
public class AuthorService {

    private final RestClient restClient;

    @Autowired
    CustomIDTokenValidator customIDTokenValidator;

    public AuthorService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<AuthorDTO> getAuthorById(Long id) throws MalformedURLException, ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        customIDTokenValidator.validate(customUserDetails.getAccessToken());
        return restClient.get()
                .uri("/api/v1/authors/{id}", id)
                .header("Authorization", "Bearer " + customUserDetails.getAccessToken())
                .retrieve()
                .toEntity(AuthorDTO.class);
    }
}
