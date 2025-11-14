package com.bnguimgo.springboot.security.tymeleafloginform.controler;

import com.bnguimgo.springboot.security.tymeleafloginform.modele.User;
import com.bnguimgo.springboot.security.tymeleafloginform.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.text.ParseException;

//@RestController
@Controller
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

    @GetMapping("/sign/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    //public String addUser(@Valid User user, BindingResult result, Model model) {
    public String addUser(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        //userRepository.save(user);
        return "redirect:home";
    }

}
