package com.bnguimgo.springboot.security.tymeleafloginform.controler;

import com.bnguimgo.springboot.security.tymeleafloginform.modele.User;
import com.bnguimgo.springboot.security.tymeleafloginform.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final LoginService loginService;

    public UserController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable(value = "email") String email) throws MalformedURLException, ParseException {
        return loginService.findByEmail(email);
    }

}
