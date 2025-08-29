package com.bnguimgo.springboot.security.tymeleafloginform.modele;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Autorisation {

    private Utilisateur utilisateur;

    private RoleEnum role;
}
