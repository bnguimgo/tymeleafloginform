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

import java.text.ParseException;

@Service
public class AuthorService {

    private final RestClient restClient;

    @Autowired
    CustomIDTokenValidator customIDTokenValidator;

    public AuthorService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<AuthorDTO> getAuthorById(Long id) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication) {
            throw new RuntimeException("Authentication required ");
        }
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        if(null == customUserDetails) {
            throw new RuntimeException("UserDetails required ");
        }
        String idToken = customUserDetails.getIdToken();
        customIDTokenValidator.validate(idToken);
        return restClient.get()
                .uri("/api/v1/authors/{id}", id)
                .header("Authorization", "Bearer " + idToken)
                .retrieve()
                .toEntity(AuthorDTO.class);
    }
}
