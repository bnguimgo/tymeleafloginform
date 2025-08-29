package com.bnguimgo.springboot.security.tymeleafloginform.security.succes;

import com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails.CustomUserDetails;
import com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails.CustomUserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    CustomUserDetailsServiceImpl userDetailsService;
    @Autowired
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        DefaultOidcUser authenticatedUser = (DefaultOidcUser) authentication.getPrincipal();
        Map<String, Object> authUserAttributes = authenticatedUser.getAttributes();
        String userName = authUserAttributes.get("username").toString();
        authentication.setAuthenticated(true);
        log.info("Authentication successful with username : {}", userName);

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        //userDetails.setAccessToken(getAccessToken().getTokenValue());
        userDetails.setAccessToken(getIdToken().getTokenValue());
            UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);

        //If authentication success
        response.sendRedirect(request.getContextPath() + "/");
    }

    private OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2AuthorizeRequest authRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(token.getAuthorizedClientRegistrationId())
                    .principal(token)
                    .build();
            OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(authRequest);
            OAuth2AccessToken accessToken = client.getAccessToken();
            log.info("Token raw value: {}", accessToken.getTokenValue());
            log.info("Token scopes: {}", accessToken.getScopes());
            return accessToken;
        }
        throw new IllegalStateException("Oauth2 Security Context not found!");
    }

    /**
     * Source: <a href="https://gpiskas.com/posts/id-token-access-token-spring-boot-spring-cloud-azure/">...</a>
     * @return OidcIdToken
     */
    private OidcIdToken getIdToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken token && token.getPrincipal() instanceof DefaultOidcUser user) {
            OidcIdToken idToken = user.getIdToken();
            log.info("Token raw value: {}", idToken.getTokenValue());
            log.info("Token claims map: {}", idToken.getClaims());
            return idToken;

        }
        throw new IllegalStateException("Oauth2 Security Context not found!");
    }
}