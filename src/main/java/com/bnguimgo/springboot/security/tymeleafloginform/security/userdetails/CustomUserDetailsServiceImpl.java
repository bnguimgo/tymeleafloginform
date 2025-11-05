package com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails;

import com.bnguimgo.springboot.security.tymeleafloginform.modele.*;
import com.bnguimgo.springboot.security.tymeleafloginform.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginService loginService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername called");
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = loginService.loginByEmail(username).getBody();

            CustomUserDetails userDetails = new CustomUserDetails(user.getFirstName(), user.getLastName(), buildAuthoritiesFromRole(user.getRoles()));
            log.info("loadUserByUsername successful");
            return userDetails;

        } catch (MalformedURLException | ParseException e) {
            throw new RuntimeException(e);
        }
/*        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(username);
        utilisateur.setDisplayName("Nguimgo" +" " +"Bertrand");
        utilisateur.setRoles(Set.of(RoleEnum.ADMIN.name(), RoleEnum.MANAGER.name()));
        Autorisation autorisation_admin = new Autorisation();
        autorisation_admin.setRole(RoleEnum.ADMIN);
        Autorisation autorisation_manager = new Autorisation();
        autorisation_manager.setRole(RoleEnum.MANAGER);
        utilisateur.setAutorisations(Set.of(autorisation_admin, autorisation_manager));
        CustomUserDetails userDetails = new CustomUserDetails(utilisateur.getUsername(), utilisateur.getDisplayName(), buildAuthorities(utilisateur.getAutorisations()));
        log.info("loadUserByUsername successful");
        return userDetails;*/
    }

    private Set<GrantedAuthority> buildAuthorities(Set<Autorisation> autorisations) {
        Set<GrantedAuthority> authorities = new HashSet<>(autorisations.size());
        for (Autorisation autorisation : autorisations) {
            RoleEnum role = autorisation.getRole();
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    private Set<GrantedAuthority> buildAuthoritiesFromRole(Collection<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>(roles.size());
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
