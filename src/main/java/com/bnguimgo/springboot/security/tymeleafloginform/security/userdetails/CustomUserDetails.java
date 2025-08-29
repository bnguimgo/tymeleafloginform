package com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final String username;
    @Getter
    private final String displayName;
    @Getter
    @Setter
    private String accessToken;
    private final Set<GrantedAuthority> authorities;
    @Getter
    private final Set<String> roles;
    //private final String displayRoles;

    public CustomUserDetails(String username, String displayName, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.displayName = displayName;
        this.authorities = Set.copyOf(authorities);
        this.roles = Set.copyOf(buildRoles(authorities));
        //this.displayRoles = displayRoles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "[NOT USED]";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    Set<String> buildRoles(Set<GrantedAuthority> authorities) {
        Set<String> roles = new HashSet<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public boolean hasAnyRole(String... anyRole) {
        for (String role : anyRole) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

}
