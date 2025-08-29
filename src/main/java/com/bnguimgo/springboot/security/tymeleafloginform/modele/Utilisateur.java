package com.bnguimgo.springboot.security.tymeleafloginform.modele;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Utilisateur {

    private String username;
    private String displayName;
    private Set<Autorisation> autorisations;
    private Set<String> roles;

}
