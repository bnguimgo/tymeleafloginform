package com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails;

import com.bnguimgo.springboot.security.tymeleafloginform.modele.Autorisation;
import com.bnguimgo.springboot.security.tymeleafloginform.modele.RoleEnum;
import com.bnguimgo.springboot.security.tymeleafloginform.modele.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername called");
        Utilisateur utilisateur = new Utilisateur();
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
        return userDetails;
    }

    private Set<GrantedAuthority> buildAuthorities(Set<Autorisation> autorisations) {
        Set<GrantedAuthority> authorities = new HashSet<>(autorisations.size());
        for (Autorisation autorisation : autorisations) {
            RoleEnum role = autorisation.getRole();
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }
}
