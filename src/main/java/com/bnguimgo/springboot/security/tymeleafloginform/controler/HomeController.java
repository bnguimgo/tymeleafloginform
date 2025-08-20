package com.bnguimgo.springboot.security.tymeleafloginform.controler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/login")
public class HomeController {

/*    @RequestMapping(value={"/", "/login"})
    public String loginForm() {

        return "login";
    }

    @RequestMapping("/homepage")
    public String showHome() {

        return "homepage";
    }*/

/*    @RequestMapping("/logout/oauth2/code/cognito")
    public String showHome() {

        return "logoutPage";
    }*/

/*    @GetMapping("/logout")
    public String redirectToLogin(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        //return new ModelAndView("redirect:/logout/oauth2/code/cognito");
        authentication.setAuthenticated(false);
        return "logout";
    }*/
}