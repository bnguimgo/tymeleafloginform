package com.bnguimgo.springboot.security.tymeleafloginform.modele;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
//Source: https://www.baeldung.com/role-and-privilege-for-spring-security-registration
public class Role {

    private Long id;
    private String name;

/*    @JsonIgnore
    private Collection<User> users;
    @JsonIgnore
    private Collection<Privilege> privileges;*/

    public Role(String name) {
        this.name = name;
    }
}