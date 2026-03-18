package com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails;

import com.bnguimgo.springboot.security.tymeleafloginform.modele.*;
import com.bnguimgo.springboot.security.tymeleafloginform.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@NullMarked //Permet de préciser qu'on n'accepte pas des paramètres nulls pour des méthodes surchargées --> exemple loadUserByUsername(final String username). Ici username n'accepte pas de valeurs nulles
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginService loginService;

    @Override
    public CustomUserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername called");
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = loginService.findByEmail(username);

            if(user != null) {
                CustomUserDetails userDetails = new CustomUserDetails(user.getFirstName(), user.getFirstName() +" " +user.getLastName(), buildAuthoritiesFromRole(user.getRoles()));
                log.info("loadUserByUsername successful");
                return userDetails;
            } else {
                throw new UsernameNotFoundException("Utilisateur inconnu de l'application ");
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<GrantedAuthority> buildAuthoritiesFromRole(Set<String> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>(roles.size());
        for(String role: roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
