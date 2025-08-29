package com.bnguimgo.springboot.security.tymeleafloginform.modele;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ADMIN("Administrateur de l'application"),

    MANAGER("Responsable gestion bibliothèque"),

    USER("Utilisateur"),

    INVITE("Invité");

    private final String label;

    RoleEnum(String label) {
        this.label = label;
    }

}
