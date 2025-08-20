package com.bnguimgo.springboot.security.tymeleafloginform.service;

import com.bnguimgo.springboot.security.tymeleafloginform.dto.AuthorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorService {

    private final RestClient restClient;

    public AuthorService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<AuthorDTO> getAuthorById(Long id) {

        return restClient.get()
                .uri("/api/v1/authors/{id}", id)
                .retrieve()
                .toEntity(AuthorDTO.class);
    }
}
