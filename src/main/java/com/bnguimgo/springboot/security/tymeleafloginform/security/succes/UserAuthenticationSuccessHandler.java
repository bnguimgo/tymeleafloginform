package com.bnguimgo.springboot.security.tymeleafloginform.security.succes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

/*    @Autowired
    UserDetailsService userDetailsService;*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        DefaultOidcUser authenticatedUser = (DefaultOidcUser) authentication.getPrincipal();
        Map<String, Object> authUserAttributes = authenticatedUser.getAttributes();
        String userName = authUserAttributes.get("username").toString();
        authentication.setAuthenticated(true);
        log.info("Authentication successful with username : {}", userName);

/*
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
*/

        //If authentication success
        response.sendRedirect(request.getContextPath() + "/");
    }
}