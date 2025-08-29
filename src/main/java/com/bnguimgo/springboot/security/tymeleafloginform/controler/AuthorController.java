package com.bnguimgo.springboot.security.tymeleafloginform.controler;

import com.bnguimgo.springboot.security.tymeleafloginform.dto.AuthorDTO;
import com.bnguimgo.springboot.security.tymeleafloginform.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") Long id) throws MalformedURLException, ParseException {
        return authorService.getAuthorById(id);
    }

/*    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") Long id) {
        return (ResponseEntity<AuthorDTO>) authorService.getAuthorById(id);
    }*/

/*    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){

    }*/
}
